package com.peazy.supplier.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.entity.SupplierUserEntity;
import com.peazy.supplier.repository.SupplierUserRepository;
import com.peazy.supplier.service.interfaces.SupplierUserService;

@Service
public class SupplierUserServiceImpl implements SupplierUserService {

	@Autowired
	private SupplierUserRepository supplierUserRepository;

	@Override
	public List<SupplierUserEntity> getUsers() throws JsonProcessingException {
		List<SupplierUserEntity> userEntities = supplierUserRepository.findAll();
		return userEntities;
	}

}
