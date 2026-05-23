package com.ikigaisoftware.erp_lite.infrastructure.persistence.jpa.repository;

import com.ikigaisoftware.erp_lite.infrastructure.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}