package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.QueryCheckOrderBean;
import com.peazy.supplier.model.dto.CheckOrderItemDto;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.request.QueryCheckOrderRequest;
import com.peazy.supplier.model.response.QueryCheckOrderItemResponse;
import com.peazy.supplier.model.response.QueryCheckOrderResponse;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.repository.SupplierProductViewRepository;
import com.peazy.supplier.service.interfaces.CheckOrderService;

@Service
public class CheckOrderServiceimpl implements CheckOrderService {

    @Autowired
    private SupplierProductRepository supplierProductRepository;

    @Autowired
    private SupplierProductViewRepository supplierProductViewRepository;

    @Override
    public QueryCheckOrderResponse queryAllCheckOrder() throws JsonProcessingException {
        QueryCheckOrderResponse response = new QueryCheckOrderResponse();
        List<CheckOrderItemDto> checkOrderItemDtos = supplierProductRepository.queryCheckOrder(null, null);
        List<QueryCheckOrderBean> queryCheckOrderItemBeanList = new ArrayList<>();

        for (CheckOrderItemDto checkOrderItemDto : checkOrderItemDtos) {
            QueryCheckOrderBean queryCheckOrderItemBean = new QueryCheckOrderBean();
            BeanUtils.copyProperties(checkOrderItemDto, queryCheckOrderItemBean);
            queryCheckOrderItemBeanList.add(queryCheckOrderItemBean);
        }
        response.setQueryCheckOrderList(queryCheckOrderItemBeanList);

        return response;
    }

    @Override
    public QueryCheckOrderResponse queryCheckOrder(QueryCheckOrderRequest req) throws JsonProcessingException {
        QueryCheckOrderResponse response = new QueryCheckOrderResponse();
        List<CheckOrderItemDto> checkOrderItemDtos = supplierProductRepository.queryCheckOrder(req.getProductName(),
                req.getSku());
        List<QueryCheckOrderBean> queryCheckOrderItemBeanList = new ArrayList<>();

        for (CheckOrderItemDto checkOrderItemDto : checkOrderItemDtos) {
            QueryCheckOrderBean queryCheckOrderItemBean = new QueryCheckOrderBean();
            BeanUtils.copyProperties(checkOrderItemDto, queryCheckOrderItemBean);
            queryCheckOrderItemBeanList.add(queryCheckOrderItemBean);
        }
        response.setQueryCheckOrderList(queryCheckOrderItemBeanList);

        return response;
    }

    @Override
    public QueryCheckOrderItemResponse queryCheckOrderItem(Long seqNo) {
        List<SupplierProductViewEntity> queryCheckOrderItemList = supplierProductViewRepository
                .queryOrderProductBySeqNo(seqNo);
        QueryCheckOrderItemResponse response = new QueryCheckOrderItemResponse();
        response.setSupplierProductViewList(queryCheckOrderItemList);
        return response;
    }
}
