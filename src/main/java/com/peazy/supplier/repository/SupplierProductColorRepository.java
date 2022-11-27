package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductColorEntity;

@Repository
public interface SupplierProductColorRepository extends JpaRepository<SupplierProductColorEntity, Long> {

    List<SupplierProductColorEntity> findByProductSeqNo(String productSeqNo);

}