package vn.atdigital.iot.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StringDetailDTO {
    String stringName;
    String cellBrand;
    String cellModel;
    Double cellQty;
    Double cNominal;
    Double vNominal;
}
