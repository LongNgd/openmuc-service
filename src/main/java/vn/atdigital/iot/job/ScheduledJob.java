package vn.atdigital.iot.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.atdigital.iot.common.enums.Status;
import vn.atdigital.iot.domain.model.SoHSchedule;
import vn.atdigital.iot.repository.SoHScheduleRepository;
import vn.atdigital.iot.service.AsyncService;

import java.time.LocalDateTime;
import java.util.List;

import static vn.atdigital.iot.common.enums.DischargeState.*;

@Service
@RequiredArgsConstructor
public class ScheduledJob {
    private final AsyncService asyncService;
    private final SoHScheduleRepository soHScheduleRepository;

    @Scheduled(cron = "0 * * * * *")
    private void runSoHSchedule() {
        LocalDateTime now = LocalDateTime.now();
        List<SoHSchedule> qualifiedSchedules = soHScheduleRepository.findByStartDatetimeBeforeAndStateAndStatus(now, PENDING, Status.ACTIVE);

        for (SoHSchedule soHSchedule : qualifiedSchedules) {
            soHSchedule.setState(RUNNING);
            soHScheduleRepository.save(soHSchedule);
            asyncService.calculateSoh(soHSchedule.getId(), soHSchedule.getStrId());
        }
    }
}
