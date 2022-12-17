package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.entity.SupplierProductViewEntity;

import lombok.Data;

@Data
public class QueryOrderProductDetailResponse {
    private List<SupplierProductViewEntity> orderProductDetailList;
}
