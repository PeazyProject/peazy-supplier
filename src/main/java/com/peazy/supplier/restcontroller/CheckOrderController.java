package com.peazy.supplier.restcontroller;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping(value = "/queryCheckOrder")
    public ResponseEntity<QueryCheckOrderResponse> queryCheckOrder() throws JsonProcessingException {
        log.info("queryCheckOrder = ");
        QueryCheckOrderResponse queryCheckOrderResponse = checkOrderService.queryCheckOrder();
        log.info("queryCheckOrder = ");
        return ResponseEntity.ok(queryCheckOrderResponse);
    }

    @GetMapping(value = "/queryCheckOrderItemBySeqNo/{seqNo}")
    public ResponseEntity<QueryCheckOrderItemResponse> queryCheckOrderItemBySeqNo(@PathVariable Long seqNo)
            throws JsonProcessingException {
        log.info("queryCheckOrderItemBySeqNo req= {}", seqNo);
        QueryCheckOrderItemResponse queryProductBySeqNoResponse = checkOrderService.queryCheckOrderItem(seqNo);
        log.info("queryCheckOrderItemBySeqNo resp= {}", queryProductBySeqNoResponse);
        return ResponseEntity.ok(queryProductBySeqNoResponse);
    }
}
