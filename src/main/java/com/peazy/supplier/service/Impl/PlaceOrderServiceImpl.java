package com.peazy.supplier.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.PlaceOrderBean;
import com.peazy.supplier.model.entity.SupplierProductColorSizeMappingEntity;
import com.peazy.supplier.model.entity.SupplierProductViewEntity;
import com.peazy.supplier.model.entity.SupplierVendorEntity;
import com.peazy.supplier.repository.SupplierProductColorSizeMappingRepository;
import com.peazy.supplier.repository.SupplierProductViewRepository;
import com.peazy.supplier.repository.SupplierVendorRepository;
import com.peazy.supplier.service.interfaces.PlaceOrderService;
import com.peazy.supplier.utils.StringUtils;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    private SupplierProductViewRepository supplierProductViewRepository;

    @Autowired
    private SupplierProductColorSizeMappingRepository supplierProductColorSizeMappingRepository;

    @Autowired
    private SupplierVendorRepository supplierVendorRepository;

    public List<SupplierVendorEntity> getVendorList() throws JsonProcessingException {
        return supplierVendorRepository.findAll();
    }

    public List<PlaceOrderBean> getOrderProductList(Long vendorSeqNo, String type)
            throws JsonProcessingException {
        List<SupplierProductViewEntity> list = supplierProductViewRepository.queryOrderProduct(vendorSeqNo, type);
        List<PlaceOrderBean> orderProductList = new ArrayList<>();
        // Map<PlaceOrderBean, BigDecimal> map;
        list.stream().forEach(entity -> {
            Optional<PlaceOrderBean> placeOrderBeanOpt = orderProductList.stream()
                    .filter(placeOrderBean -> placeOrderBean.getProductSeqNo() == entity.getProductSeqNo()).findFirst();
            if (placeOrderBeanOpt.isPresent()) {
                placeOrderBeanOpt.get()
                        .setNotOrderCnt(placeOrderBeanOpt.get().getNotOrderCnt().add(entity.getNotOrderCnt()));
            } else {
                PlaceOrderBean bean = new PlaceOrderBean();
                bean.setProductSeqNo(entity.getProductSeqNo());
                bean.setProductName(entity.getProductName());
                bean.setSku(entity.getSku());
                bean.setCategory(entity.getCategory());
                bean.setNotOrderCnt(entity.getNotOrderCnt());
                orderProductList.add(bean);
            }
        });
        return orderProductList;
    }

    public List<SupplierProductViewEntity> getOrderProductDetailList(Long productSeqNo)
            throws JsonProcessingException {
        return supplierProductViewRepository.queryOrderProductBySeqNo(productSeqNo);
    }

    public void orderProducts(List<SupplierProductViewEntity> orderProductList) throws JsonProcessingException {
        List<SupplierProductColorSizeMappingEntity> pcsmList = new ArrayList<>();
        orderProductList.forEach(orderProduct -> {
            Optional<SupplierProductColorSizeMappingEntity> pcsmOptinal = supplierProductColorSizeMappingRepository
                    .findById(orderProduct.getPcsmSeqNo());
            if (pcsmOptinal.isPresent()) {
                SupplierProductColorSizeMappingEntity pcsm = pcsmOptinal.get();
                pcsm.setOrderedCnt(pcsm.getOrderedCnt().add(pcsm.getNotOrderCnt()));
                pcsm.setNotOrderCnt(BigDecimal.ZERO);
                pcsmList.add(pcsm);
            }
        });
        supplierProductColorSizeMappingRepository.saveAll(pcsmList);
    }

    public String exportPlaceOrder(List<SupplierProductViewEntity> orderProductList) {
        StringBuilder sb = new StringBuilder();
        orderProductList.forEach(orderProduct -> {
            sb.append(StringUtils.getPlaceOrderString(orderProduct)).append("\n\r");
        });
        return sb.toString();
    }
}
