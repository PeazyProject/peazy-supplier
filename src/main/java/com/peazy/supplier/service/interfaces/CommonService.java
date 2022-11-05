package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.DropDownBean;

public interface CommonService {
	List<DropDownBean> getDropDownList(String mainCategory, String subCategory) throws JsonProcessingException;
}
