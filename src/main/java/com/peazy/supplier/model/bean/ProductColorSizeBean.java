package com.peazy.supplier.model.bean;

import java.util.List;

import lombok.Data;

@Data
public class ProductColorSizeBean {
    String colorSeqNo;
    List<ProductSizeQtyBean> productSizeQtyList;
}
