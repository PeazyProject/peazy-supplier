package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.PlaceOrderDetailBean;

import lombok.Data;

@Data
public class QueryOrderProductDetailResponse {
    private List<PlaceOrderDetailBean> orderProductDetailList;
}
