package vn.atdigital.iot.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class ChannelValue {
    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "flag")
    private Integer flag;

    @Column(name = "VALUE")
    private Integer value;
}
