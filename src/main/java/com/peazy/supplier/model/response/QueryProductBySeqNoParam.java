package com.peazy.supplier.model.response;

import java.math.BigDecimal;
import java.util.List;

import com.peazy.supplier.model.bean.ProductColorSizeBean;

import lombok.Data;

@Data
public class QueryProductBySeqNoParam {

    private String productName;
    private List<Long> skuList;
    private List<Long> mpnList;
    private List<String> sizeList;
    private List<String> colorList;
    private BigDecimal cost;
    private BigDecimal price;
    private String category;
    private String productStatus;
    private String productDesc;
    private String mainPic;
    private List<String> picList;
    private List<ProductColorSizeBean> productColorSizeList;
    
}
