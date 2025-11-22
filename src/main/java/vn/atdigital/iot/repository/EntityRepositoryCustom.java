package vn.atdigital.iot.repository;

public interface EntityRepositoryCustom {
    Double getCurrentValue(String strId);
    Double getTemperatureValue(String strId);
    Double getSocValue(String strId);
}
