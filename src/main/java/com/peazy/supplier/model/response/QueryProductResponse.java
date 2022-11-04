package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.QueryProductBean;

import lombok.Data;

@Data
public class QueryProductResponse {
    List<QueryProductBean> queryProductList;
}
