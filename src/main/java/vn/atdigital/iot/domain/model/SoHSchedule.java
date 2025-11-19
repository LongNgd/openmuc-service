package vn.atdigital.iot.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.atdigital.iot.common.enums.StatusEnums;

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

    @Column(name = "string_id")
    private String stringId;

    private Double soh;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "finish_datetime")
    private LocalDateTime finishDatetime;

    private StatusEnums status;
}
