package com.example.campingontop.domain.mysql.orders.service;

import com.example.campingontop.domain.mysql.cart.model.Cart;
import com.example.campingontop.domain.mysql.cart.repository.CartRepository;
import com.example.campingontop.common.BaseResponse;
import com.example.campingontop.domain.mysql.coupon.model.Coupon;
import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrdersException;
import com.example.campingontop.exception.entityException.UserException;
import com.example.campingontop.domain.mysql.house.model.House;
import com.example.campingontop.domain.mysql.orders.model.OrderedHouse;
import com.example.campingontop.domain.mysql.orders.model.Orders;
import com.example.campingontop.domain.mysql.orders.model.dto.response.GetOrdersListRes;
import com.example.campingontop.domain.mysql.orders.model.dto.response.GetPortOneRes;
import com.example.campingontop.domain.mysql.orders.model.dto.response.PostOrderInfoRes;
import com.example.campingontop.domain.mysql.orders.repository.OrderedHouseRespository;
import com.example.campingontop.domain.mysql.orders.repository.OrdersRepository;
import com.example.campingontop.domain.mysql.user.model.User;
import com.example.campingontop.domain.mysql.user.repository.queryDsl.UserRepository;
import com.example.campingontop.domain.mysql.userCoupon.model.UserCoupon;
import com.example.campingontop.domain.mysql.userCoupon.repository.UserCouponRepository;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrderedHouseRespository orderedHouseRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final UserCouponRepository userCouponRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Transactional
    public BaseResponse<List<PostOrderInfoRes>> createOrder(User user, String impUid, Integer finalAmount, Long couponId) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = paymentService.getPaymentInfo(impUid);
        Integer amount = iamportResponse.getResponse().getAmount().intValue();

        if (!finalAmount.equals(amount)) {
            throw new OrdersException(ErrorCode.NOT_MATCH_AMOUNT);
        }

        String customDataString = iamportResponse.getResponse().getCustomData();
        log.info("Received customDataString: {}", customDataString);

        // Check and correct the format of customDataString if necessary
        if (customDataString.startsWith("\"") && customDataString.endsWith("\"")) {
            customDataString = customDataString.substring(1, customDataString.length() - 1).replace("\\\"", "\"");
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<GetPortOneRes>>(){}.getType();
        List<GetPortOneRes> paymentHouses;
        try {
            paymentHouses = gson.fromJson(customDataString, listType);
        } catch (JsonSyntaxException e) {
            log.error("JSON syntax error: {}", e.getMessage());
            throw new OrdersException(ErrorCode.INVALID_JSON_FORMAT);
        }

        List<PostOrderInfoRes> orderList = new ArrayList<>();

        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if (result.isPresent()) {
            Orders order = ordersRepository.save(Orders.toEntity(impUid, user.getEmail(), amount));

            for (GetPortOneRes getPortOneRes : paymentHouses) {
                Cart cart = cartRepository.findById(getPortOneRes.getId())
                        .orElseThrow(() -> new RuntimeException("cart not found with id: " + getPortOneRes.getId()));
                orderedHouseRepository.save(OrderedHouse.toEntity(order, cart));

                orderList.add(PostOrderInfoRes.toEntity(impUid, getPortOneRes, order));
            }

            // 쿠폰 사용 처리
//            List<UserCoupon> userCoupons = userCouponRepository.findByUser(user);
//            for (UserCoupon userCoupon : userCoupons) {
//                userCoupon.setUsed(true);
//                userCouponRepository.save(userCoupon);
//            }
            // 쿠폰 사용 처리
            if (couponId != null) {
                Optional<UserCoupon> userCoupon = userCouponRepository.findByCouponId(couponId);
                if (userCoupon != null) {
                    UserCoupon userCouponResult= userCoupon.get();
                    userCouponResult.setUsed(true);
                    userCouponRepository.save(userCouponResult);
                }
            }

            return BaseResponse.successResponse("주문 완료", orderList);
        }
        throw new UserException(ErrorCode.MEMBER_NOT_EXIST);
    }


    public BaseResponse<List<GetOrdersListRes>> orderList(User user) {
        String email = user.getEmail();
        List<GetOrdersListRes> result = new ArrayList<>();
        if (email != null) {
            List<Orders> ordersList = ordersRepository.findAllByConsumerEmailOrderByIdDesc(email);
            for (Orders order : ordersList) {
                for (OrderedHouse orderedHouse : order.getOrderedHouseList()) {
                    Cart cart = orderedHouse.getCart();
                    if (cart != null) {
                        House house = cart.getHouse();
                        if (house != null) {
                            GetOrdersListRes orderDetails = GetOrdersListRes.toEntity(order, house, cart);
                            result.add(orderDetails);
                        }
                    }
                }
            }
            return BaseResponse.successResponse("주문 내역 조회.", result);
        } else {
            return BaseResponse.failResponse(500, "해당 이메일을 가진 유저가 존재하지 않습니다.");
        }
    }
}