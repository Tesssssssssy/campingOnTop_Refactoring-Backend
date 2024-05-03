package com.example.campingontop.orderedHouse.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.OrderedHouseException;
import com.example.campingontop.orderedHouse.model.OrderedHouse;
import com.example.campingontop.orderedHouse.model.dto.request.PostOrderedHouseDtoReq;
import com.example.campingontop.orderedHouse.model.dto.response.PostOrderedHouseDtoRes;
import com.example.campingontop.orderedHouse.repository.OrderedHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderedHouseService {
    private final OrderedHouseRepository orderedHouseRepository;

    public PostOrderedHouseDtoRes createOrderedHouse(PostOrderedHouseDtoReq req) {
        OrderedHouse orderedHouse = OrderedHouse.builder()
                .orders(req.getOrders())
                .house(req.getHouse())
                .build();

        orderedHouse = orderedHouseRepository.save(orderedHouse);

        PostOrderedHouseDtoRes response = PostOrderedHouseDtoRes.builder()
                .id(orderedHouse.getId())
                .orders(orderedHouse.getOrders())
                .house(orderedHouse.getHouse())
                .build();

        return response;
    }

    public List<PostOrderedHouseDtoRes> findOrderedHouseList(Long userId) {
        List<OrderedHouse> result = orderedHouseRepository.findOrderedListByUserId(userId);

        if (!result.isEmpty()) {
            List<PostOrderedHouseDtoRes> orderedHouseDtoResList = result.stream()
                    .map(orderedHouse -> {
                        return PostOrderedHouseDtoRes.builder()
                                .id(orderedHouse.getId())
                                .orders(orderedHouse.getOrders())
                                .house(orderedHouse.getHouse())
                                .build();
                    })
                    .collect(Collectors.toList());

            return orderedHouseDtoResList;
        }
        throw new OrderedHouseException(ErrorCode.ORDEREDHOUSE_NOT_EXIST);
    }


}
