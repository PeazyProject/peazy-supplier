package com.peazy.supplier.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.args.ChangePasswordRequest;
import com.peazy.supplier.model.args.QueryUserRequest;
import com.peazy.supplier.model.dto.QueryUserDto;
import com.peazy.supplier.service.interfaces.CustomerUserService;
import com.peazy.supplier.service.interfaces.SupplierUserService;

@CrossOrigin
@RestController
@RequestMapping(path = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CustomerUserService customerUserService;

	@Autowired
	private SupplierUserService supplierUserService;

	@PostMapping(value = "/getCustomerUsers")
	public ResponseEntity<List<QueryUserDto>> getCustomerUsers(@RequestBody QueryUserRequest model) throws JsonProcessingException {
		List<QueryUserDto> result = customerUserService.getUsers(model);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping(value = "/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest model) {
		customerUserService.changePassword(model);
		return ResponseEntity.ok(null);
	}

	@PostMapping(value = "/getSupplierUsers")
	public ResponseEntity<List<QueryUserDto>> getSupplierUsers(@RequestBody QueryUserRequest model) throws JsonProcessingException {
		List<QueryUserDto> result = supplierUserService.getUsers(model);
		return ResponseEntity.ok(result);
	}

}
