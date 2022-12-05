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
import com.peazy.supplier.model.bean.DropDownBean;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductBySeqNoParam;
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
	public ResponseEntity<List<DropDownBean>> getProductSizeOption() throws JsonProcessingException {

		List<DropDownBean> result = productService.getProductSizeOption();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/getProductColorOption")
	public ResponseEntity<List<DropDownBean>> getProductColorOption() throws JsonProcessingException {

		List<DropDownBean> result = productService.getProductColorOption();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/getProductCategoryOption")
	public ResponseEntity<List<DropDownBean>> getProductCategoryOption() throws JsonProcessingException {

		List<DropDownBean> result = productService.getProductCategoryOption();
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/queryProductBySeqNo/{seqNo}")
	public ResponseEntity<QueryProductBySeqNoParam> queryProductBySeqNo(@PathVariable Long seqNo)
			throws JsonProcessingException {
		logger.info("queryProductBySeqNo = {}", seqNo);
		QueryProductBySeqNoParam queryProductBySeqNoParam = productService.queryProductBySeqNo(seqNo);
		logger.info("queryProductBySeqNoParam = {}", queryProductBySeqNoParam);
		return ResponseEntity.ok(queryProductBySeqNoParam);
	}

	@PostMapping(value = "/editProduct")
	public ResponseEntity<Void> editProduct(@RequestBody QueryProductBySeqNoParam queryProductBySeqNoParam)
			throws JsonProcessingException {
		logger.info("editProduct request = {}", queryProductBySeqNoParam);
		productService.editProduct(queryProductBySeqNoParam);
		return ResponseEntity.ok(null);
	}
}
