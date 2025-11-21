package vn.atdigital.iot.service;

import vn.atdigital.iot.domain.dto.StringDetailDTO;

import java.util.Map;

public interface LatestValueService {
    Map<String, String> getDevValues();
    String getSiteName();
    StringDetailDTO getStringDetails(String stringId);
}
