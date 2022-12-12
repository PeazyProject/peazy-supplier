package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.peazy.supplier.model.entity.SupplierSkuEntity;

@Repository
public interface SupplierProductSkuRepository extends JpaRepository<SupplierSkuEntity, Long> {

    List<SupplierSkuEntity> findByProductSeqNo(Long productSeqNo);

    @Transactional
    void deleteByProductSeqNo(Long productSeqNo);

}