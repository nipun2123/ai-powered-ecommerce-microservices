package com.devicehub.repository;

import com.devicehub.entity.PhoneSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PhoneSpecRepository extends JpaRepository<PhoneSpec, UUID> {

}
