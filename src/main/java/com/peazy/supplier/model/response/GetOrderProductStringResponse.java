package com.peazy.supplier.model.response;

import java.util.List;

import lombok.Data;

@Data
public class GetOrderProductStringResponse {
    private List<String> orderProductStringList;
}
