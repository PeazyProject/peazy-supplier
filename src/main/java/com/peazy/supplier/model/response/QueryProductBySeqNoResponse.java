package com.peazy.supplier.model.response;

import java.util.List;

import com.peazy.supplier.model.bean.ProductColorSizeBean;

import lombok.Data;

@Data
public class QueryProductBySeqNoResponse {

    private String productName;
    private List<String> skuList;
    private List<String> sizeList;
    private List<String> colorList;
    private int cost;
    private int price;
    private String category;
    private String productStatus;
    private String productDesc;
    private String mainPic;
    private List<String> picList;
    private List<ProductColorSizeBean> productColorSizeList;
    
}
