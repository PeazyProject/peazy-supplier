package com.peazy.supplier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peazy.supplier.model.entity.CommonPictureEntity;

@Repository
public interface CommonPictureRepository extends JpaRepository<CommonPictureEntity, Long> {

    
}