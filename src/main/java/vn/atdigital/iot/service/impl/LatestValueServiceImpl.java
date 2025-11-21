package vn.atdigital.iot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.atdigital.iot.domain.dto.StringDetailDTO;
import vn.atdigital.iot.domain.model.LatestValue;
import vn.atdigital.iot.repository.LatestValueRepository;
import vn.atdigital.iot.service.LatestValueService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LatestValueServiceImpl implements LatestValueService {
    private final LatestValueRepository latestValueRepository;

    @Override
    public Map<String, String> getDevValues() {
        List<LatestValue> devValues = latestValueRepository.findByChannelIdStartingWith("dev_");

        Map<String, String> devValueMap = parseValues(devValues);
        Assert.isTrue(!devValueMap.isEmpty(), "Dev values not found.");

        return devValueMap;
    }

    @Override
    public String getSiteName() {
        List<LatestValue> siteNames = latestValueRepository.findByChannelIdStartingWith("site_name");
        Assert.isTrue(!siteNames.isEmpty(), "Site name not found.");
        Assert.isTrue(siteNames.size() == 1, "More than 1 site name found.");

        return siteNames.getFirst().getValueString();
    }

    @Override
    public StringDetailDTO getStringDetails(String stringId) {
        String prefixKey = "str" + stringId;
        List<LatestValue> stringValues = latestValueRepository.findByChannelIdStartingWith("str"+stringId);

        Map<String, String> stringValueMap = parseValues(stringValues);
        Assert.isTrue(!stringValueMap.isEmpty(), "String values not found.");

        return StringDetailDTO.builder()
                .stringName(stringValueMap.get(prefixKey + "_string_name"))
                .cellBrand(stringValueMap.get(prefixKey + "_cell_brand"))
                .cellModel(stringValueMap.get(prefixKey + "_cell_model"))
                .cellQty(Double.valueOf(stringValueMap.get(prefixKey + "_cell_qty")))
                .cNominal(Double.valueOf(stringValueMap.get(prefixKey + "_Cnominal")))
                .vNominal(Double.valueOf(stringValueMap.get(prefixKey + "_Vnominal")))
                .build();
    }

    @Override
    public void deleteString(String stringId) {
        String prefixKey = "str" + stringId;
        List<LatestValue> stringValues = latestValueRepository.findByChannelIdStartingWith("str"+stringId);
        Assert.isTrue(!stringValues.isEmpty(), "String not found.");

        latestValueRepository.deleteAllByChannelIdStartingWith(prefixKey);
    }

    private Map<String,String> parseValues(List<LatestValue> values) {
        Map<String,String> valuesMap = new TreeMap<>();

        for (LatestValue value : values) {
            if (Objects.equals(value.getValueType(), "S")) {
                valuesMap.put(value.getChannelId(), value.getValueString());
            } else if (Objects.equals(value.getValueType(), "D")) {
                valuesMap.put(value.getChannelId(), String.valueOf(value.getValueDouble()));
            } else {
                throw new RuntimeException("Unknown value type: " + value.getValueType());
            }
        }

        return valuesMap;
    }
}
