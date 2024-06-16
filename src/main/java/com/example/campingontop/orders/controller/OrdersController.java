package com.example.campingontop.orders.controller;

import com.example.campingontop.common.BaseResponse;
import com.example.campingontop.coupon.service.CouponService;
import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrdersException;
import com.example.campingontop.orders.model.dto.response.GetOrdersListRes;
import com.example.campingontop.orders.model.dto.response.PostOrderInfoRes;
import com.example.campingontop.orders.service.OrdersService;
import com.example.campingontop.orders.service.PaymentService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Tag(name="Orders", description = "Orders CRUD")
@Api(tags = "Orders")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final CouponService couponService;
    private final PaymentService paymentService;

    @ApiOperation(value = "상품 주문")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "impUid", value = "주문 번호 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = "")})
    @RequestMapping(method = RequestMethod.POST, value = "/validation")
    public BaseResponse<List<PostOrderInfoRes>> ordersCreate(@AuthenticationPrincipal User user,
                                                             @RequestBody Map<String, String> requestBody) {
        String impUid = requestBody.get("impUid");
        Integer finalAmount = Integer.parseInt(requestBody.get("finalAmount"));
        log.info("Received impUid for validation: {}, finalAmount: {}", impUid, finalAmount);
        try {
            if(paymentService.paymentValidation(impUid, finalAmount)){
                log.info("Payment validation successful, creating order...");
                return ordersService.createOrder(user, impUid, finalAmount);
            } else {
                log.error("Payment validation failed for impUid: {}", impUid);
                throw new OrdersException(ErrorCode.NOT_MATCH_AMOUNT);
            }
        } catch (Exception e) {
            log.error("Server error occurred", e);
            throw new OrdersException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //Consumer의 주문 내역을 확인
    @ApiOperation(value = "주문 내역 조회")
    @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
            required = true, paramType = "query", dataType = "string", defaultValue = "")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse<List<GetOrdersListRes>> orderList(@AuthenticationPrincipal User user) {
        return ordersService.orderList(user);
    }

    //Consumer가 구매를 취소
    @ApiOperation(value = "주문 취소")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "impUid", value = "취소할 주문의 주문 번호 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""))
    @RequestMapping(method = RequestMethod.GET, value = "/cancel")
    public BaseResponse<String> orderCancel(String impUid) throws IOException {
        return paymentService.paymentCancel(impUid);
    }
}
