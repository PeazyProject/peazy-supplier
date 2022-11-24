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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductBySeqNoResponse;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.service.interfaces.ProductService;

@CrossOrigin
@RestController
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/queryProduct")
	public ResponseEntity<QueryProductResponse> queryProduct(@RequestBody QueryProductRequest queryProductRequest)
			throws JsonProcessingException {
		logger.info("queryProductRequest = {}", queryProductRequest);
		QueryProductResponse queryProductResponse = productService.queryProduct(queryProductRequest);
		logger.info("queryProductResponse = {}", queryProductResponse);
		return ResponseEntity.ok(queryProductResponse);
	}

	@GetMapping(value = "/getImgUrl/{snCode}")
	public ResponseEntity<Object> getImgUrl(@PathVariable String snCode) throws JsonProcessingException {

		BlobDocumentBean result = productService.getImgUrl(snCode);
		return ResponseEntity.status(HttpStatus.OK)
				.header("content-disposition", "attachment; filename=\"" + result.getName() + "\"")
				.body(result.getContent());
	}

	@GetMapping(value = "/getProductSizeOption")
	public ResponseEntity<List<String>> getProductSizeOption() throws JsonProcessingException {

		List<String> result = productService.getProductSizeOption();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/getProductColorOption")
	public ResponseEntity<List<String>> getProductColorOption() throws JsonProcessingException {

		List<String> result = productService.getProductColorOption();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/getProductCategoryOption")
	public ResponseEntity<List<String>> getProductCategoryOption() throws JsonProcessingException {

		List<String> result = productService.getProductCategoryOption();
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/queryProduct/{seqNo}")
	public ResponseEntity<QueryProductBySeqNoResponse> queryProductBySeqNo(@PathVariable String seqNo)
			throws JsonProcessingException {
		logger.info("queryProductBySeqNo = {}", seqNo);
		QueryProductBySeqNoResponse queryProductBySeqNoResponse = productService.queryProductBySeqNo(seqNo);
		logger.info("queryProductBySeqNoResponse = {}", queryProductBySeqNoResponse);
		return ResponseEntity.ok(queryProductBySeqNoResponse);
	}

}
