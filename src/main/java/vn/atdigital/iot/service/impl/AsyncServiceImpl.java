package vn.atdigital.iot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.atdigital.iot.common.enums.DischargeState;
import vn.atdigital.iot.common.util.TemperatureFactor;
import vn.atdigital.iot.domain.model.LatestValue;
import vn.atdigital.iot.domain.model.SoHSchedule;
import vn.atdigital.iot.repository.EntityRepository;
import vn.atdigital.iot.repository.LatestValueRepository;
import vn.atdigital.iot.repository.SoHScheduleRepository;
import vn.atdigital.iot.service.AsyncService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static vn.atdigital.iot.common.Constants.ASYNC_PARAMS.INTERVAL;
import static vn.atdigital.iot.common.enums.DischargeState.PENDING;
import static vn.atdigital.iot.common.enums.DischargeState.RUNNING;
import static vn.atdigital.iot.common.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements AsyncService {
    private final SoHScheduleRepository soHScheduleRepository;
    private final LatestValueRepository latestValueRepository;
    private final EntityRepository entityRepository;
    @Async
    @Override
    public void calculateSoh(Long id, String strId) {
        try {
            while(Thread.currentThread().isAlive()) {
                Optional<SoHSchedule> scheduleOptional = soHScheduleRepository.findByIdAndStateInAndStatus(id, Arrays.asList(PENDING, RUNNING),ACTIVE);
                if (scheduleOptional.isEmpty())
                    Thread.currentThread().interrupt();

                SoHSchedule soHSchedule = scheduleOptional.get();
                if (soHSchedule.getState().equals(DischargeState.STOPPED)) {
                    stopThreadSuccess(soHSchedule);
                    return;
                }
                Optional<LatestValue> cNominalValueOpt = latestValueRepository.findLatestValueByChannelId(strId + "_Cnominal");
                if (cNominalValueOpt.isEmpty()) {
                    stopThreadFail(soHSchedule);
                    return;
                }

                LatestValue cNominalValue = cNominalValueOpt.get();
                if (Objects.isNull(cNominalValue.getValueDouble())) {
                    stopThreadFail(soHSchedule);
                    return;
                }

                double cNominalAh = cNominalValue.getValueDouble(); // Ah
                double cNominalAs = cNominalAh * 3600;
                double usedQ = soHSchedule.getSoh();
                double current = entityRepository.getCurrentValue(strId);
                double temperature = entityRepository.getTemperatureValue(strId);
                usedQ += current * INTERVAL * TemperatureFactor.getFactor(temperature);
                usedQ = usedQ / cNominalAs;

                soHSchedule.setSoh(usedQ);
                soHSchedule.setUpdateDatetime(LocalDateTime.now());
                soHScheduleRepository.save(soHSchedule);

                // Simulate a long-running task
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        CompletableFuture.completedFuture("Task completed");
    }

    private CompletableFuture<String> stopThreadSuccess(SoHSchedule soHSchedule){
        soHSchedule.setState(DischargeState.SUCCESS);
        soHSchedule.setEndDatetime(LocalDateTime.now());
        soHScheduleRepository.save(soHSchedule);
        return CompletableFuture.completedFuture("Task completed successfully");
    }

    private CompletableFuture<String> stopThreadFail(SoHSchedule soHSchedule){
        soHSchedule.setState(DischargeState.FAILED);
        soHSchedule.setEndDatetime(LocalDateTime.now());
        soHScheduleRepository.save(soHSchedule);
        return CompletableFuture.completedFuture("Task completed failed");
    }
}
