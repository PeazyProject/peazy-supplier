package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductColorSizeMappingEntity;

@Repository
public interface SupplierProductColorSizeMappingRepository
                extends JpaRepository<SupplierProductColorSizeMappingEntity, Long> {

        List<SupplierProductColorSizeMappingEntity> findByProductSeqNo(Long productSeqNo);
}
