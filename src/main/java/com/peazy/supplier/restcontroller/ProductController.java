package com.peazy.supplier.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.entity.SupplierProductEntity;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.service.interfaces.ProductService;

@CrossOrigin
@RestController
@RequestMapping(path = "/product",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/queryProduct")
	public ResponseEntity<QueryProductResponse> queryProduct() throws JsonProcessingException {
		QueryProductResponse result = productService.queryProduct();
		logger.info("getSupplierUsers = {}", result);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/getImgUrl/{snCode}")
	public ResponseEntity<Object> getImgUrl(@PathVariable String snCode) throws JsonProcessingException {

		logger.info("getImgUrl snCode = {}", snCode);
		BlobDocumentBean result = productService.getImgUrl(snCode);
		return ResponseEntity.status(HttpStatus.OK)
			.header("content-disposition", "attachment; filename=\"" + result.getName() + "\"").body(result.getContent());
	}

}
