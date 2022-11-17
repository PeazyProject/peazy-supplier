package com.peazy.supplier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.peazy.supplier.model.entity.ErrorCodeEntity;

public interface ErrorCodeRepository extends JpaRepository<ErrorCodeEntity, Long> {

	@Query(value = "  select * "
			+ "   from alanlee.Common_ErrorCode "
			+ "	  where Category = :category and ErrorCode = :errorCode and Lang=:lang ",nativeQuery = true)
	public Optional<ErrorCodeEntity> findByCategoryAndErrorCode(
		@Param("category") String category,
		@Param("errorCode")  String code, 
		@Param("lang")  String lang);
		
}