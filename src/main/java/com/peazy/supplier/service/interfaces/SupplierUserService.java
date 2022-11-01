package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.entity.SupplierUserEntity;

public interface SupplierUserService {
	public List<SupplierUserEntity> getUsers() throws JsonProcessingException;
}
