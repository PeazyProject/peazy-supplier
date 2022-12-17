package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.PlaceOrderBean;

import lombok.Data;

@Data
public class QueryOrderProductResponse {
    private List<PlaceOrderBean> orderProductList;
}
