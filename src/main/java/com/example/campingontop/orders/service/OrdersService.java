package com.example.campingontop.orders.service;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.repository.CartRepository;
import com.example.campingontop.common.BaseResponse;
import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.UserException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.orders.model.OrderedHouse;
import com.example.campingontop.orders.model.Orders;
import com.example.campingontop.orders.model.PaymentHouses;
import com.example.campingontop.orders.model.dto.response.GetOrdersListRes;
import com.example.campingontop.orders.model.dto.response.GetPortOneRes;
import com.example.campingontop.orders.model.dto.response.PostOrderInfoRes;
import com.example.campingontop.orders.repository.OrderedHouseRespository;
import com.example.campingontop.orders.repository.OrdersRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import com.example.campingontop.utils.JwtUtils;
import com.google.gson.Gson;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrderedHouseRespository orderedHouseRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final CartRepository cartRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Transactional
    public BaseResponse<List<PostOrderInfoRes>> createOrder(User user, String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = paymentService.getPaymentInfo(impUid);
        Integer amount = iamportResponse.getResponse().getAmount().intValue();
        String customDataString = iamportResponse.getResponse().getCustomData();
        customDataString = "{\"carts\":" + customDataString + "}";
        Gson gson = new Gson();
        PaymentHouses paymentHouses = gson.fromJson(customDataString, PaymentHouses.class);

        List<PostOrderInfoRes> orderList = new ArrayList<>();

        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if (result.isPresent()) {
            Orders order = ordersRepository.save(Orders.toEntity(impUid, user.getEmail(), amount));

            // Custom Data 안에 있던 House 리스트 하나씩 꺼내와서 OrderedHouse에 저장
            for (GetPortOneRes getPortOneRes : paymentHouses.getCarts()) {
                Cart cart = cartRepository.findById(getPortOneRes.getId())
                        .orElseThrow(() -> new RuntimeException("cart not found with id: " + getPortOneRes.getId()));
                orderedHouseRepository.save(OrderedHouse.toEntity(order, cart));

                orderList.add(PostOrderInfoRes.toEntity(impUid, getPortOneRes, order));
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

