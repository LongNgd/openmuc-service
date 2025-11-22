package vn.atdigital.iot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.atdigital.iot.common.enums.Status;
import vn.atdigital.iot.domain.dto.ScheduleDTO;
import vn.atdigital.iot.domain.model.SoHSchedule;
import vn.atdigital.iot.repository.SoHScheduleRepository;
import vn.atdigital.iot.service.SoHService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static vn.atdigital.iot.common.enums.DischargeState.*;
import static vn.atdigital.iot.common.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class SoHServiceImpl implements SoHService {
    private final SoHScheduleRepository soHScheduleRepository;

    @Override
    public void createSchedule(String strId, LocalDateTime startDate,Double current) {
        boolean alreadyExist = checkAvailableSchedule(strId);
        if(alreadyExist) throw new RuntimeException("SoH schedule already exists.");

        SoHSchedule soHSchedule = SoHSchedule.builder()
                .strId(strId)
                .current(current)
                .usedQ(0D)
                .startDatetime(startDate)
                .state(PENDING)
                .status(ACTIVE)
                .build();
        soHScheduleRepository.save(soHSchedule);
    }


    @Override
    public void updateSchedule(Long id, LocalDateTime time) {
        SoHSchedule pendingSchedule = soHScheduleRepository.findByIdAndStateAndStatus(id, PENDING,ACTIVE)
                .orElseThrow(() -> new RuntimeException("SoH schedule not found."));
        pendingSchedule.setStartDatetime(time);
        soHScheduleRepository.save(pendingSchedule);
    }

    @Override
    public void stopSchedule(Long id) {
        SoHSchedule runningSchedule = soHScheduleRepository.findByIdAndStateAndStatus(id, RUNNING,ACTIVE)
                .orElseThrow(() -> new RuntimeException("SoH schedule not found."));
        runningSchedule.setState(STOPPED);
        soHScheduleRepository.save(runningSchedule);
    }

    @Override
    public void removeSchedule(Long id) {
        SoHSchedule pendingSchedule = soHScheduleRepository.findByIdAndStateAndStatus(id, PENDING,ACTIVE)
                .orElseThrow(() -> new RuntimeException("SoH schedule not found."));
        pendingSchedule.setStatus(Status.INACTIVE);
        soHScheduleRepository.save(pendingSchedule);
    }

    @Override
    public List<ScheduleDTO> getListSchedule() {
        List<ScheduleDTO> result = new ArrayList<>();
        List<SoHSchedule> activeSchedules = soHScheduleRepository.findByStatus(ACTIVE)
                .orElseThrow(() -> new RuntimeException("SoH schedule not found."));
        for(SoHSchedule schedule : activeSchedules) {
            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .id(schedule.getId())
                    .strId(schedule.getStrId())
                    .current(schedule.getCurrent())
                    .soh(schedule.getSoh())
                    .state(schedule.getState())
                    .startTime(schedule.getStartDatetime())
                    .endTime(schedule.getEndDatetime())
                    .build();
            result.add(scheduleDTO);
        }
        return result;

    }

    private boolean checkAvailableSchedule(String strId) {
        Optional<SoHSchedule> availableSchedule = soHScheduleRepository.findByStrIdAndStateInAndStatus(strId, Arrays.asList(PENDING, RUNNING),ACTIVE);
        return availableSchedule.isPresent();
    }
}
