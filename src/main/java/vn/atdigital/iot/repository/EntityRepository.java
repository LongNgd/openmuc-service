package vn.atdigital.iot.repository;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.atdigital.iot.domain.model.ChannelValue;

import java.time.LocalDateTime;

@Repository
public interface EntityRepository extends JpaRepository<ChannelValue, LocalDateTime>, EntityRepositoryCustom {
}
