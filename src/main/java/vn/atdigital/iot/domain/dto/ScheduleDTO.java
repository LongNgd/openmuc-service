package vn.atdigital.iot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.atdigital.iot.common.enums.DischargeState;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO implements Serializable {
    private long id;
    private String strId;
    private Double current;
    private Double soh;
    private DischargeState state;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
