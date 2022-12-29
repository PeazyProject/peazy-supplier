package com.peazy.supplier.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.enumerate.PlaceOrderErrorCodeEnumImpl;
import com.peazy.supplier.enumerate.QueryTypeEnum;
import com.peazy.supplier.exception.ErrorCodeException;
import com.peazy.supplier.model.request.OrderProductsRequest;
import com.peazy.supplier.model.response.GetOrderProductStringResponse;
import com.peazy.supplier.model.response.QueryOrderProductDetailResponse;
import com.peazy.supplier.model.response.QueryOrderProductResponse;
import com.peazy.supplier.model.response.QueryVendorResponse;
import com.peazy.supplier.service.interfaces.PlaceOrderService;

@CrossOrigin
@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaceOrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlaceOrderService placeOrderService;

    @GetMapping(value = "/getVendorList")
    public ResponseEntity<QueryVendorResponse> queryVendorList()
            throws JsonProcessingException {
        QueryVendorResponse response = new QueryVendorResponse();
        response.setVendorList(placeOrderService.getVendorList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/queryOrderProductList")
    public ResponseEntity<QueryOrderProductResponse> queryOrderProductList(Long vendorSeqNo)
            throws JsonProcessingException {
        logger.info("queryOrderProductList vendorSeqNo : {}", vendorSeqNo);
        QueryOrderProductResponse response = new QueryOrderProductResponse();
        response.setOrderProductList(placeOrderService.getOrderProductList(vendorSeqNo));
        logger.info("response = {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/queryOrderProductDetailList")
    public ResponseEntity<QueryOrderProductDetailResponse> queryOrderProductDetailList(Long productSeqNo,
            QueryTypeEnum type)
            throws JsonProcessingException {
        logger.info("queryOrderProductDetailList productSeqNo = {}, type = {}", productSeqNo, type);
        QueryOrderProductDetailResponse response = new QueryOrderProductDetailResponse();
        response.setOrderProductDetailList(placeOrderService.getOrderProductDetailList(productSeqNo, type));
        logger.info("response = {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/orderProducts")
    public HttpStatus orderProducts(
            @RequestBody OrderProductsRequest request)
            throws JsonProcessingException {
        logger.info("orderProducts = {}", request);
        try {
            if (!CollectionUtils.isEmpty(request.getSeqList())) {
                placeOrderService.orderProducts(request.getSeqList());
            }
        } catch (Exception e) {
            throw new ErrorCodeException(PlaceOrderErrorCodeEnumImpl.ORDER_PRODUCTS_FAIL);
        }
        return HttpStatus.OK;
    }

    @PostMapping(value = "/getPlaceOrderString")
    public ResponseEntity<GetOrderProductStringResponse> getPlaceOrderString(
            @RequestBody OrderProductsRequest request)
            throws JsonProcessingException {
        logger.info("request : {}", request);
        GetOrderProductStringResponse response = new GetOrderProductStringResponse();
        response.setOrderProductStringList(placeOrderService.exportPlaceOrder(request.getSeqList()));
        return ResponseEntity.ok(response);
    }
}
