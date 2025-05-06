package com.devicehub.repository;

import com.devicehub.entity.TabletSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TabletSpecRepository extends JpaRepository<TabletSpec, UUID> {
}
