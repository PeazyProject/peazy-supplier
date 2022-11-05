package com.peazy.supplier.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.DropDownBean;
import com.peazy.supplier.service.interfaces.CommonService;

@CrossOrigin
@RestController
@RequestMapping(path = "/supplierCommon",produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonService commonService;

	@GetMapping(value = "/getDropDownList/{mainCategory}/{subCategory}")
	public ResponseEntity<List<DropDownBean>> getDropDownList(
		@PathVariable String mainCategory, @PathVariable String subCategory) throws JsonProcessingException {

		List<DropDownBean> dropDownList = commonService.getDropDownList(mainCategory, subCategory);
		return ResponseEntity.ok(dropDownList);
	}

}
