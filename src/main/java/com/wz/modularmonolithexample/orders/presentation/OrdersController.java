package com.wz.modularmonolithexample.orders.presentation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wz.modularmonolithexample.orders.application.OrderCreateDTO;
import com.wz.modularmonolithexample.orders.application.OrderDTO;
import com.wz.modularmonolithexample.orders.application.OrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(Pageable pageable) {
        return toResponseEntity(ordersService.getOrders(pageable));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable String id) {
        return ordersService.getOrder(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        return ordersService.createOrder(orderCreateDTO);
    }

    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable String id) {
        ordersService.confirm(id);
    }

    private <T> ResponseEntity<List<T>> toResponseEntity(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total", String.valueOf(page.getTotalElements()));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
