package vn.atdigital.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.iot.common.enums.StatusEnums;
import vn.atdigital.iot.domain.model.SoHSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoHScheduleRepository extends JpaRepository<SoHSchedule,Long> {
    Optional<SoHSchedule> findByStringIdAndStatusIn(String stringId, List<StatusEnums> statuses);

    Optional<SoHSchedule> findByStringIdAndStatus(String stringId, StatusEnums statusEnums);

    List<SoHSchedule> findByStartDatetimeBeforeAndStatus(LocalDateTime startDatetimeBefore, StatusEnums status);
}
