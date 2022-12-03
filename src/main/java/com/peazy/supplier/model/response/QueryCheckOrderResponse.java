package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.QueryCheckOrderBean;

import lombok.Data;

@Data
public class QueryCheckOrderResponse {
    private List<QueryCheckOrderBean> queryCheckOrderList;
}
