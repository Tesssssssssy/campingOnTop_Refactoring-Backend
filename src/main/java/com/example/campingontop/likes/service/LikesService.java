package com.example.campingontop.likes.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.LikesException;
import com.example.campingontop.house.model.response.GetFindHouseDtoResForLikes;
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
        Likes existingLike = likesRepository.findByUserIdAndHouseId(req.getUserId(), req.getHouseId());

        if (existingLike != null) {
            existingLike.setStatus(true);
            House house = existingLike.getHouse();
            house.increaseLikeCount();
            houseRepository.save(house);
            likesRepository.save(existingLike);
        } else {
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

            existingLike = likes;
        }

        House house1 = existingLike.getHouse();
        List<HouseImage> houseImageList = house1.getHouseImageList();

        List<String> filenames = new ArrayList<>();
        for (HouseImage productImage : houseImageList) {
            String filename = productImage.getFilename();
            filenames.add(filename);
        }

        GetFindHouseDtoRes houseDto = GetFindHouseDtoRes.toDto(house1, filenames);

        GetUserWithLikesDtoRes res = GetUserWithLikesDtoRes.builder()
                .id(existingLike.getId())
                .email(existingLike.getUser().getEmail())
                .name(existingLike.getUser().getName())
                .likedHouse(houseDto)
                .build();

        return res;
    }

    @Transactional(readOnly = true)
    public GetLikesDtoRes findLikesByUserId(Long userId) {
        List<Likes> list = likesRepository.findLikesByUserId(userId);
        if (!list.isEmpty()) {
            List<GetFindHouseDtoResForLikes> likesInfoList = list.stream()
                    .map(likes -> {
                        House house = likes.getHouse();
                        List<HouseImage> houseImageList = house.getHouseImageList();
                        List<String> filenames = houseImageList.stream()
                                .map(HouseImage::getFilename)
                                .collect(Collectors.toList());
                        return GetFindHouseDtoResForLikes.builder()
                                .id(likes.getId())
                                .houseId(house.getId())
                                .houseName(house.getName())
                                .address(house.getAddress())
                                .price(house.getPrice())
                                .filenames(filenames)
                                .build();
                    })
                    .collect(Collectors.toList());
            return GetLikesDtoRes.builder()
                    .likesList(likesInfoList)
                    .build();
        }
        return null;
    }

    @Transactional
    public Boolean deleteLikes(Long likesId) {
        Optional<Likes> result = likesRepository.findById(likesId);
        if (result.isPresent()) {
            Likes likes = result.get();
            likes.setStatus(false);
            likesRepository.save(likes);

            House house = likes.getHouse();
            house.decreaseLikeCount();
            houseRepository.save(house);

            return true;
        }
        throw new LikesException(ErrorCode.LIKES_NOT_EXIST);
    }
}
