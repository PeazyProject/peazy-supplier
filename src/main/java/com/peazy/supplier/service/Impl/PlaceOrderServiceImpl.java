package com.peazy.supplier.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.enumerate.QueryTypeEnum;
import com.peazy.supplier.model.bean.PlaceOrderBean;
import com.peazy.supplier.model.bean.PlaceOrderDetailBean;
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

    public List<PlaceOrderBean> getOrderProductList(Long vendorSeqNo)
            throws JsonProcessingException {
        List<SupplierProductViewEntity> list = supplierProductViewRepository.queryOrderProduct(vendorSeqNo);
        List<PlaceOrderBean> orderProductList = new ArrayList<>();
        // Map<PlaceOrderBean, BigDecimal> map;
        list.stream().filter(
                entity -> entity.getNotOrderCnt().compareTo(BigDecimal.ZERO) > 0)
                .forEach(entity -> {
                    Optional<PlaceOrderBean> placeOrderBeanOpt = orderProductList.stream()
                            .filter(placeOrderBean -> placeOrderBean.getProductSeqNo()
                                    .compareTo(entity.getProductSeqNo()) == 0)
                            .findFirst();
                    if (placeOrderBeanOpt.isPresent()) {
                        placeOrderBeanOpt.get()
                                .setNotOrderCnt(placeOrderBeanOpt.get().getNotOrderCnt().add(entity.getNotOrderCnt()));
                    } else {
                        PlaceOrderBean bean = new PlaceOrderBean(entity);
                        orderProductList.add(bean);
                    }
                });
        return orderProductList;
    }

    public List<PlaceOrderDetailBean> getOrderProductDetailList(Long productSeqNo, QueryTypeEnum type)
            throws JsonProcessingException {
        List<SupplierProductViewEntity> orderProductList = supplierProductViewRepository
                .queryOrderProductBySeqNo(productSeqNo);
        List<PlaceOrderDetailBean> detailList = new ArrayList<>();
        orderProductList.forEach(entity -> {
            BigDecimal productCnt = BigDecimal.ZERO;
            switch (type) {
                case NOT_ORDER:
                    productCnt = entity.getNotOrderCnt();
                    break;
                case STOCK:
                    productCnt = entity.getOrderedCnt().add(entity.getCheckOrderCnt());
                    break;
            }
            PlaceOrderDetailBean bean = new PlaceOrderDetailBean(entity);
            bean.setProductCnt(productCnt);
            detailList.add(bean);
        });
        return detailList;
    }

    public void orderProducts(List<Long> seqList) throws JsonProcessingException {
        List<SupplierProductColorSizeMappingEntity> pcsmList = new ArrayList<>();
        seqList.forEach(seq -> {
            Optional<SupplierProductColorSizeMappingEntity> pcsmOptinal = supplierProductColorSizeMappingRepository
                    .findById(seq);
            if (pcsmOptinal.isPresent()) {
                SupplierProductColorSizeMappingEntity pcsm = pcsmOptinal.get();
                pcsm.setOrderedCnt(pcsm.getOrderedCnt().add(pcsm.getNotOrderCnt()));
                pcsm.setNotOrderCnt(BigDecimal.ZERO);
                pcsmList.add(pcsm);
            }
        });
        supplierProductColorSizeMappingRepository.saveAll(pcsmList);
    }

    public List<String> exportPlaceOrder(List<Long> seqList) {
        List<String> orderProductStringList = new ArrayList<>();
        List<SupplierProductViewEntity> orderProductList = supplierProductViewRepository
                .queryOrderProductByPCSMSeqNo(seqList);
        orderProductList.forEach(orderProduct -> {
            orderProductStringList.add(StringUtils.getPlaceOrderString(orderProduct));
        });
        return orderProductStringList;
    }
}
