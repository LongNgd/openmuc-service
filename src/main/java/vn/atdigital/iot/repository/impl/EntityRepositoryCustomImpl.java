package vn.atdigital.iot.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import vn.atdigital.iot.repository.EntityRepositoryCustom;

import java.util.Collections;
import java.util.List;

@Slf4j
public class EntityRepositoryCustomImpl implements EntityRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Double getCurrentValue(String strId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ti.\"VALUE\" AS value\n")
                .append("FROM ").append(strId).append("_total_i ti\n")
                .append("WHERE 1=1\n")
                .append("ORDER BY time DESC\n")
                .append("LIMIT 1");
        Query query = em.createNativeQuery(sb.toString());
        List<Integer> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            double value = result.getFirst();
            return value/100;
        }
        return 0D;
    }

    @Override
    public Double getTemperatureValue(String strId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT at.\"VALUE\" AS value\n")
                .append("FROM ").append(strId).append("_ambient_t at\n")
                .append("WHERE 1=1\n")
                .append("ORDER BY time DESC\n")
                .append("LIMIT 1");
        Query query = em.createNativeQuery(sb.toString());
        List<Integer> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            double value = result.getFirst();
            return value/100;
        }
        return 0D;
    }
}
