package com.ikigaisoftware.erp_lite.infrastructure.persistence.jpa.repository;

import com.ikigaisoftware.erp_lite.infrastructure.persistence.jpa.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProductEntity, UUID> {
}

