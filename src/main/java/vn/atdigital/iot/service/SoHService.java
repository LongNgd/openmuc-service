package vn.atdigital.iot.service;

import vn.atdigital.iot.domain.dto.ScheduleDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SoHService {
    void createSchedule(String strId, LocalDateTime startTime,Double current);
    void updateSchedule(Long id, LocalDateTime startTime);
    void stopSchedule(Long id);
    void removeSchedule(Long id);
    List<ScheduleDTO> getListSchedule();
}
