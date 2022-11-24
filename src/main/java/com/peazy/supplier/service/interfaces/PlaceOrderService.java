package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.entity.SupplierVendorEntity;

public interface PlaceOrderService {
    List<SupplierVendorEntity> getVendorList() throws JsonProcessingException;

    List<SupplierProductViewEntity> getOrderProductList(Long vendor, boolean isNotOrder) throws JsonProcessingException;

    void orderProducts(List<SupplierProductViewEntity> orderProductList) throws JsonProcessingException;

    String exportPlaceOrder(List<SupplierProductViewEntity> orderProductList) throws JsonProcessingException;
}
