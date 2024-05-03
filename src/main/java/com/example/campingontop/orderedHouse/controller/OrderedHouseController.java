package com.example.campingontop.orderedHouse.controller;

import com.example.campingontop.orderedHouse.model.dto.request.PostOrderedHouseDtoReq;
import com.example.campingontop.orderedHouse.model.dto.response.PostOrderedHouseDtoRes;
import com.example.campingontop.orderedHouse.service.OrderedHouseService;
import com.example.campingontop.user.model.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="OrderedHouse", description = "OrderedHouse 숙소 결제 내역 CRUD")
@Api(tags = "OrderedHouse")
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/orderedHouse")
public class OrderedHouseController {
    private final OrderedHouseService orderedHouseService;

    @PostMapping("/create")
    public ResponseEntity createOrdersProduct(PostOrderedHouseDtoReq req) {
        PostOrderedHouseDtoRes res = orderedHouseService.createOrderedHouse(req);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/list")
    public ResponseEntity findOrdersProductList(@AuthenticationPrincipal User user) {
        List<PostOrderedHouseDtoRes> list = orderedHouseService.findOrderedHouseList(user.getId());
        return ResponseEntity.ok().body(list);
    }


}
