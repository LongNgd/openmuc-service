package vn.atdigital.iot.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "latest_values")
public class LatestValue {
    @Id
    @Column(name = "channelid")
    private String channelId;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "value_double")
    private Double valueDouble;

    @Column(name = "value_string")
    private String valueString;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Column(name = "updated_at")
    private LocalDateTime updatedDatetime;
}
