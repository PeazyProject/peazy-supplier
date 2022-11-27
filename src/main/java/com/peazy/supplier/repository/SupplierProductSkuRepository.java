package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductSkuEntity;

@Repository
public interface SupplierProductSkuRepository extends JpaRepository<SupplierProductSkuEntity, Long> {

    List<SupplierProductSkuEntity> findByProductSeqNo(String productSeqNo);

}