package com.peazy.supplier.utils;

import com.peazy.supplier.model.entity.SupplierProductViewEntity;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String getPlaceOrderString(SupplierProductViewEntity spv) {
        // sku - color size cnt
        StringBuilder sb = new StringBuilder();
        sb.append(spv.getSku()).append(" - ").append(spv.getColor()).append(" ");
        if (!StringUtils.equals("F", spv.getSize())) {
            sb.append(spv.getSize()).append(" ");
        }
        sb.append(spv.getNotOrderCnt());
        return sb.toString();
    }
}
