package com.peazy.supplier.enumerate;

import lombok.Getter;

@Getter
public enum QueryTypeEnum {
    NOT_ORDER("notOrder"),
    STOCK("stock");

    private String type;

    QueryTypeEnum(String type) {
        this.type = type;
    }
}
