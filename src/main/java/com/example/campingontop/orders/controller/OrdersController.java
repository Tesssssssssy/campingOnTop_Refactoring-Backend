package com.example.campingontop.orders.controller;

import com.example.campingontop.orders.model.dto.request.PostCreateOrdersDtoReq;
import com.example.campingontop.orders.model.dto.response.PostCreateOrdersDtoRes;
import com.example.campingontop.orders.service.OrdersService;
import com.example.campingontop.user.model.User;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Tag(name="Orders", description = "Orders CRUD")
@Api(tags = "Orders")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    // 실질적으로 결제를 검증하는 메소드
    @RequestMapping("/validation")
    public ResponseEntity paymentValidation(PostCreateOrdersDtoReq req) {
        try {
            if (ordersService.paymentValidation(req)) {
                return ResponseEntity.ok().body("ok");
            } else {
                return ResponseEntity.ok().body("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/create")
    public ResponseEntity createOrders(PostCreateOrdersDtoReq req) {
        PostCreateOrdersDtoRes res = null;
        try {
            res = ordersService.createOrders(req);
        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(res);
    }

//    @GetMapping("/list")
//    public ResponseEntity findOrdersList(@AuthenticationPrincipal User user) throws IamportResponseException, IOException {
//        List<PostCreateOrdersDtoRes> dtoList = ordersService.findOrdersList(user);
//        return ResponseEntity.ok().body(dtoList);
//    }
}
