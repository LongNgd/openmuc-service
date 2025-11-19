package vn.atdigital.iot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.atdigital.iot.common.enums.StatusEnums;
import vn.atdigital.iot.domain.model.SoHSchedule;
import vn.atdigital.iot.repository.SoHScheduleRepository;
import vn.atdigital.iot.service.SoHService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static vn.atdigital.iot.common.enums.StatusEnums.*;

@Service
@RequiredArgsConstructor
public class SoHServiceImpl implements SoHService {
    private final SoHScheduleRepository soHScheduleRepository;

    @Override
    public void createSchedule(String stringId, LocalDateTime startDate) {
        boolean alreadyExist = checkAvailableSchedule(stringId);
        if(alreadyExist) throw new RuntimeException("SoH schedule already exists.");

        SoHSchedule soHSchedule = SoHSchedule.builder()
                .stringId(stringId)
                .startDatetime(startDate)
                .status(PENDING)
                .build();
        soHScheduleRepository.save(soHSchedule);
    }

    @Override
    public void stopSchedule(String stringId) {
        SoHSchedule runningSchedule = soHScheduleRepository.findByStringIdAndStatus(stringId, RUNNING)
                .orElseThrow(() -> new RuntimeException("SoH schedule not found."));

        LocalDateTime now = LocalDateTime.now();

        runningSchedule.setFinishDatetime(now);
        runningSchedule.setStatus(FINISHED);
        soHScheduleRepository.save(runningSchedule);
    }

    private boolean checkAvailableSchedule(String stringId) {
        List<StatusEnums> statusCheckList = Arrays.asList(PENDING, RUNNING);
        Optional<SoHSchedule> availableSchedule = soHScheduleRepository.findByStringIdAndStatusIn(stringId, statusCheckList);

        return availableSchedule.isPresent();
    }
}
