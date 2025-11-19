package vn.atdigital.iot.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.atdigital.iot.common.enums.DischargeState;
import vn.atdigital.iot.common.enums.Status;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "soh_schedule")
public class SoHSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "str_id")
    private String strId;

    @Column(name = "soh")
    private Double soh;

    @Column(name = "current")
    private Double current;

    @Column(name = "state")
    private DischargeState state;

    @Column(name = "status")
    private Status status;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;
}
