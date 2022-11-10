package com.peazy.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.SupplierProductSizeEntity;

@Repository
public interface SupplierProductSizeRepository extends JpaRepository<SupplierProductSizeEntity, Long> {

}