package com.peazy.supplier.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public List<SupplierProductViewEntity> getOrderProductList(Long vendorSeqNo, String type)
            throws JsonProcessingException {
        return supplierProductViewRepository.queryOrderProduct(vendorSeqNo, type);
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
