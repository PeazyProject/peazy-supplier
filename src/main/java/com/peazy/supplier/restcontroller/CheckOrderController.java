package com.peazy.supplier.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peazy.supplier.model.request.QueryCheckOrderRequest;
import com.peazy.supplier.model.response.QueryCheckOrderItemResponse;
import com.peazy.supplier.model.response.QueryCheckOrderResponse;
import com.peazy.supplier.service.interfaces.CheckOrderService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping(path = "/checkOrder", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CheckOrderController {

    @Autowired
    private CheckOrderService checkOrderService;

    @PostMapping(value = "/queryAllCheckOrder")
    public ResponseEntity<QueryCheckOrderResponse> queryAllCheckOrder() throws JsonProcessingException {
        log.info("queryCheckOrder = ");
        QueryCheckOrderResponse queryCheckOrderResponse = checkOrderService.queryAllCheckOrder();
        log.info("queryCheckOrder = ");
        return ResponseEntity.ok(queryCheckOrderResponse);
    }

    @GetMapping(value = "/queryCheckOrderItemBySeqNo/{seqNo}")
    public ResponseEntity<QueryCheckOrderItemResponse> queryCheckOrderItemBySeqNo(@PathVariable Long seqNo)
            throws JsonProcessingException {
        log.info("queryCheckOrderItemBySeqNo req= {}", seqNo);
        QueryCheckOrderItemResponse QueryProductBySeqNoParam = checkOrderService.queryCheckOrderItem(seqNo);
        log.info("queryCheckOrderItemBySeqNo resp= {}", QueryProductBySeqNoParam);
        return ResponseEntity.ok(QueryProductBySeqNoParam);
    }

    @PostMapping(value = "/queryCheckOrder")
    public ResponseEntity<QueryCheckOrderResponse> queryCheckOrder(@RequestBody QueryCheckOrderRequest req)
            throws JsonProcessingException {
        log.info("queryCheckOrder = {}", req);
        QueryCheckOrderResponse queryCheckOrderResponse = checkOrderService.queryCheckOrder(req);
        log.info("queryCheckOrder = ",queryCheckOrderResponse);
        return ResponseEntity.ok(queryCheckOrderResponse);
    }
}
