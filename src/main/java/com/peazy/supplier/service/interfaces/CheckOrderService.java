package com.peazy.supplier.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.request.QueryCheckOrderRequest;
import com.peazy.supplier.model.response.QueryCheckOrderItemResponse;
import com.peazy.supplier.model.response.QueryCheckOrderResponse;

public interface CheckOrderService {

    QueryCheckOrderResponse queryAllCheckOrder() throws JsonProcessingException;

    QueryCheckOrderResponse queryCheckOrder(QueryCheckOrderRequest req) throws JsonProcessingException;

    QueryCheckOrderItemResponse queryCheckOrderItem(Long seqNo) throws JsonProcessingException;
}
