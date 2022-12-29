package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.enumerate.QueryTypeEnum;
import com.peazy.supplier.model.bean.PlaceOrderBean;
import com.peazy.supplier.model.bean.PlaceOrderDetailBean;
import com.peazy.supplier.model.entity.SupplierVendorEntity;

public interface PlaceOrderService {
    List<SupplierVendorEntity> getVendorList() throws JsonProcessingException;

    List<PlaceOrderBean> getOrderProductList(Long vendorSeqNo) throws JsonProcessingException;

    List<PlaceOrderDetailBean> getOrderProductDetailList(Long productSeqNo, QueryTypeEnum type)
            throws JsonProcessingException;

    void orderProducts(List<Long> seqList) throws JsonProcessingException;

    List<String> exportPlaceOrder(List<Long> seqList) throws JsonProcessingException;
}
