package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductPicEntity;

@Repository
public interface SupplierProductPicRepository extends JpaRepository<SupplierProductPicEntity, Long> {

    List<SupplierProductPicEntity> findByProductSeqNo(Long productSeqNo);

}