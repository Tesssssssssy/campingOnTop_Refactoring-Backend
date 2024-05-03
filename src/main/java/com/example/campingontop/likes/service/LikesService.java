package com.example.campingontop.likes.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.LikesException;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.response.GetFindHouseDtoRes;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.likes.model.dto.request.PostCreateLikesDtoReq;
import com.example.campingontop.likes.model.dto.response.GetLikesDtoRes;
import com.example.campingontop.likes.repository.LikesRepository;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.model.response.GetUserWithLikesDtoRes;
import com.example.campingontop.user.repository.queryDsl.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public GetUserWithLikesDtoRes createLikes(PostCreateLikesDtoReq req) {
        boolean exists = likesRepository.existsByUserIdAndHouseId(req.getUserId(), req.getHouseId());

        if (!exists) {
            User user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다. user_id: " + req.getUserId()));

            House house = houseRepository.findById(req.getHouseId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 숙소를 찾을 수 없습니다. house_id: " + req.getHouseId()));

            Likes likes = Likes.builder()
                    .user(user)
                    .house(house)
                    .status(true)
                    .build();

            likes = likesRepository.save(likes);
            house.increaseLikeCount();
            houseRepository.save(house);

            House house1 = likes.getHouse();
            List<HouseImage> houseImageList = house1.getHouseImageList();

            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes houseDto = GetFindHouseDtoRes.toDto(house1, filenames);

            GetUserWithLikesDtoRes res = GetUserWithLikesDtoRes.builder()
                    .id(likes.getId())
                    .email(likes.getUser().getEmail())
                    .name(likes.getUser().getName())
                    .likedHouse(houseDto)
                    .build();

            return res;
        }
        throw new LikesException(ErrorCode.DUPLICATED_LIKES);
    }

    @Transactional(readOnly = true)
    public GetLikesDtoRes findLikesByUserId(Long userId) {
        /*
        List<Likes> list = likesRepository.findLikesByUserId(userId);
        if (!list.isEmpty()) {
            List<GetFindHouseDtoRes> list1 = list.stream()
                    .map(likes -> {
                        House house = likes.getHouse();
                        List<HouseImage> houseImageList = house.getHouseImageList();

                        List<String> filenames = houseImageList.stream()
                                .map(HouseImage::getFilename)
                                .collect(Collectors.toList());

                        return GetFindHouseDtoRes.toDto(house, filenames);
                    })
                    .collect(Collectors.toList());

            return GetLikesDtoRes.builder()
                    .id(list.get(0).getId())
                    .email(list.get(0).getUser().getEmail())
                    .name(list.get(0).getUser().getName())
                    .houseDtoResList(list1)
                    .build();
        }
        throw new LikesException(ErrorCode.LIKES_NOT_EXIST);
        */
        List<Likes> list = likesRepository.findLikesByUserId(userId);
        if (!list.isEmpty()) {
            Map<Long, GetFindHouseDtoRes> likesInfoMap = list.stream()
                    .collect(Collectors.toMap(
                            Likes::getId,
                            likes -> {
                                House house = likes.getHouse();
                                List<HouseImage> houseImageList = house.getHouseImageList();
                                List<String> filenames = houseImageList.stream()
                                        .map(HouseImage::getFilename)
                                        .collect(Collectors.toList());
                                return GetFindHouseDtoRes.toDto(house, filenames);
                            }
                    ));
            return new GetLikesDtoRes(likesInfoMap);
        }
        throw new LikesException(ErrorCode.LIKES_NOT_EXIST);
    }

    public void deleteLikes(Long likesId) {
        Optional<Likes> result = likesRepository.findById(likesId);
        if (result.isPresent()) {
            Likes likes = result.get();
            likes.setStatus(false);
            likesRepository.save(likes);
            return;
        }
        throw new LikesException(ErrorCode.LIKES_NOT_EXIST);
    }
}
