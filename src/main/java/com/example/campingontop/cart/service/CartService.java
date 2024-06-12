package com.example.campingontop.cart.service;

import com.example.campingontop.cart.model.Cart;
import com.example.campingontop.cart.model.dto.request.PostCreateCartReq;
import com.example.campingontop.cart.model.dto.request.PatchDeleteCartDtoReq;
import com.example.campingontop.cart.model.dto.response.GetCartDtoRes;
import com.example.campingontop.cart.model.dto.response.PostCreateCartDtoRes;
import com.example.campingontop.cart.repository.CartRepository;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.CartException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public PostCreateCartDtoRes addToCart(User user, PostCreateCartReq req) {
        House house = houseRepository.findById(req.getHouseId())
                .orElseThrow(() -> new EntityNotFoundException("해당 숙소가 존재하지 않습니다: " + req.getHouseId()));

        if (isHouseAlreadyInCart(user.getId(), req.getHouseId(), req.getCheckIn(), req.getCheckOut())) {
            throw new CartException(ErrorCode.DUPLICATED_RESERVATION);
        }

        int nights = (int) ChronoUnit.DAYS.between(req.getCheckIn(), req.getCheckOut());
        int totalPrice = house.getPrice() * nights;

        LocalDate checkIn = req.getCheckIn().atStartOfDay().toLocalDate();
        LocalDate checkOut = req.getCheckOut().atStartOfDay().toLocalDate();

        Cart cart = Cart.builder()
                .user(user)
                .house(house)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .price(totalPrice)
                .status(true)
                .build();

        cart = cartRepository.save(cart);

        List<HouseImage> houseImageList = house.getHouseImageList();

        List<String> filenames = new ArrayList<>();
        for (HouseImage productImage : houseImageList) {
            String filename = productImage.getFilename();
            filenames.add(filename);
        }

        PostCreateCartDtoRes res = PostCreateCartDtoRes.toDto(cart, filenames);

        return res;
    }

    private boolean isHouseAlreadyInCart(Long userId, Long houseId, LocalDate checkIn, LocalDate checkOut) {

        Optional<Cart> existingCart = cartRepository.findByUser_IdAndHouse_IdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                userId, houseId, checkIn, checkOut);

        return existingCart.isPresent();
    }

    @Transactional(readOnly = true)
    public List<GetCartDtoRes> getCartsByUserId(Long userId) {
        List<Cart> result = cartRepository.findByUserId(userId);
        List<GetCartDtoRes> getCartDtoResList = new ArrayList<>();

        if (!result.isEmpty()) {
            for (Cart cart : result) {
                List<HouseImage> houseImageList = cart.getHouse().getHouseImageList();

                List<String> filenames = new ArrayList<>();
                for (HouseImage houseImage : houseImageList) {
                    String filename = houseImage.getFilename();
                    filenames.add(filename);
                }

                GetFindHouseDtoRes getFindHouseDtoRes = GetFindHouseDtoRes.toDto(cart.getHouse(), filenames);

                GetCartDtoRes res = GetCartDtoRes.toDto(cart, getFindHouseDtoRes);
                getCartDtoResList.add(res);
            }
            return getCartDtoResList;
        }
        return null;
    }

    @Transactional
    public Boolean deleteCart(Long deleteId) {
        Optional<Cart> result = cartRepository.findById(deleteId);
        if (result.isPresent()) {
            Cart cart = result.get();
            cart.setStatus(false);
            cartRepository.save(cart);
            return true;
        }
        throw new CartException(ErrorCode.CART_NOT_EXIST);
    }
}
