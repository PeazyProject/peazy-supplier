package com.peazy.supplier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.CommonCodeConverterEntity;

@Repository
public interface CommonCodeConverterRepository extends JpaRepository<CommonCodeConverterEntity, Long> {

    public List<CommonCodeConverterEntity> findByMainCategoryAndSubCategoryAndIsActivated(
        String mainCategory, String subCategory, String IsActivated);
    
}