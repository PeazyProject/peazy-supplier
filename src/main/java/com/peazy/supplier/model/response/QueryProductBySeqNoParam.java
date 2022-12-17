package com.peazy.supplier.model.response;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.peazy.supplier.model.bean.ProductColorSizeBean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryProductBySeqNoParam {

    private String productSeqNo;
    private String productName;
    private List<String> skuList;
    private List<String> mpnList;
    private List<String> sizeList;
    private List<String> colorList;
    private BigDecimal cost;
    private BigDecimal price;
    private String category;
    private String productStatus;
    private String productDesc;
    private String mainPicSnCode;
    private List<String> picSnCodeList;
    private List<ProductColorSizeBean> productColorSizeList;
    private String vendorSeqNo;
    private String userId;

}
