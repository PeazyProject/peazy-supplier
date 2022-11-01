package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.args.ChangePasswordRequest;
import com.peazy.supplier.model.args.QueryUserRequest;
import com.peazy.supplier.model.dto.QueryUserDto;

public interface SupplierUserService {
	public List<QueryUserDto> getUsers(QueryUserRequest model) throws JsonProcessingException;
	void changePassword(ChangePasswordRequest model);
}
