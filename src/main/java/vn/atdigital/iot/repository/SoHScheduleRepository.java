package vn.atdigital.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.iot.common.enums.DischargeState;
import vn.atdigital.iot.common.enums.Status;
import vn.atdigital.iot.domain.model.SoHSchedule;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoHScheduleRepository extends JpaRepository<SoHSchedule,Long> {
    Optional<SoHSchedule> findByIdAndStateInAndStatus(Long id, List<DischargeState> state, Status status);

    Optional<SoHSchedule> findByStrIdAndStateInAndStatus(String strId, List<DischargeState> states, Status status);

    Optional<SoHSchedule> findByStrIdAndStateAndStatus(String strId, DischargeState state, Status status);

    Optional<SoHSchedule> findByIdAndStateAndStatus(Long id, DischargeState state, Status status);

    Optional<SoHSchedule> findByIdAndStrIdAndStatus(Long id, String stringId, Status status);

    List<SoHSchedule> findByStartDatetimeBeforeAndStateAndStatus(LocalDateTime startDatetime, DischargeState state, Status status);

    Optional<List<SoHSchedule>> findByStatus(Status status);
}
