package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierMpnEntity;

@Repository
public interface SupplierProductMpnRepository extends JpaRepository<SupplierMpnEntity, Long> {

    List<SupplierMpnEntity> findByProductSeqNo(Long productSeqNo);

    void deleteByProductSeqNo(Long productSeqNo);

}