package vn.atdigital.iot.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.atdigital.iot.domain.model.SoHSchedule;
import vn.atdigital.iot.repository.SoHScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static vn.atdigital.iot.common.enums.StatusEnums.*;

@Service
@RequiredArgsConstructor
public class ScheduledJob {
    private final SoHScheduleRepository soHScheduleRepository;

    @Scheduled(cron = "0 * * * * *")
    private void runSoHSchedule() {
        LocalDateTime now = LocalDateTime.now();
        List<SoHSchedule> qualifiedSchedules = soHScheduleRepository.findByStartDatetimeBeforeAndStatus(now, PENDING);

        for (SoHSchedule soHSchedule : qualifiedSchedules) {
            soHSchedule.setStatus(RUNNING);
            soHScheduleRepository.save(soHSchedule);

            calculateSoH(soHSchedule);
        }
    }

    private void calculateSoH(SoHSchedule soHSchedule) {
        soHSchedule.setFinishDatetime(LocalDateTime.now());
        soHScheduleRepository.save(soHSchedule);
    }
}
