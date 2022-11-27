package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductMpnEntity;

@Repository
public interface SupplierProductMpnRepository extends JpaRepository<SupplierProductMpnEntity, Long> {

    List<SupplierProductMpnEntity> findByProductSeqNo(String productSeqNo);

}