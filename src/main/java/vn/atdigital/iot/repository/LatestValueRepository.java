package vn.atdigital.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.iot.domain.model.LatestValue;

import java.util.Optional;

@Repository
public interface LatestValueRepository extends JpaRepository<LatestValue,String> {
    Optional<LatestValue> findLatestValueByChannelId(String channelId);
}
