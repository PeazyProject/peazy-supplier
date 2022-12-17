package com.peazy.supplier.model.request;

import java.util.List;

import com.peazy.supplier.model.entity.SupplierProductViewEntity;

import lombok.Data;

@Data
public class ConfirmCheckOrderRequest {
    private List<SupplierProductViewEntity> supplierProductViewList;
    private String userId;
}
