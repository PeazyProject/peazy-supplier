package com.peazy.supplier.model.response;

import java.util.List;
import com.peazy.supplier.model.entity.SupplierProductColorSizeMappingEntity;

import lombok.Data;

@Data
public class QueryCheckOrderItemResponse {
    private List<SupplierProductColorSizeMappingEntity> queryCheckOrderItemBeanList;
}
