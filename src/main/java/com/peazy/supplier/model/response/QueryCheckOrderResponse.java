package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.QueryCheckOrderItemBean;

import lombok.Data;

@Data
public class QueryCheckOrderResponse {
    private List<QueryCheckOrderItemBean> queryCheckOrderItemBeanList;
}
