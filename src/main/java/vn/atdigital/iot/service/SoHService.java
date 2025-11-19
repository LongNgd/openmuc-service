package vn.atdigital.iot.service;

import java.time.LocalDateTime;

public interface SoHService {
    void createSchedule(String stringId, LocalDateTime startDate);
    void stopSchedule(String stringId);
}
