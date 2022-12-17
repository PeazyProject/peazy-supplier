package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.PlaceOrderBean;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.entity.SupplierVendorEntity;

public interface PlaceOrderService {
    List<SupplierVendorEntity> getVendorList() throws JsonProcessingException;

    List<PlaceOrderBean> getOrderProductList(Long vendorSeqNo, String type) throws JsonProcessingException;

    List<SupplierProductViewEntity> getOrderProductDetailList(Long productSeqNo) throws JsonProcessingException;

    void orderProducts(List<SupplierProductViewEntity> orderProductList) throws JsonProcessingException;

    String exportPlaceOrder(List<SupplierProductViewEntity> orderProductList) throws JsonProcessingException;
}
