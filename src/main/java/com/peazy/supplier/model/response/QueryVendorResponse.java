package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.entity.SupplierVendorEntity;

import lombok.Data;

@Data
public class QueryVendorResponse {
    private List<SupplierVendorEntity> vendorList;
}
