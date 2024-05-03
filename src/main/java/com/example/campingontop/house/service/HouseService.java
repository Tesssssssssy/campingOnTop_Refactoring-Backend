package com.example.campingontop.house.service;

import com.example.campingontop.exception.ErrorCode;
import com.example.campingontop.exception.entityException.HouseException;
import com.example.campingontop.house.model.House;
import com.example.campingontop.house.model.request.*;
import com.example.campingontop.house.model.response.*;
import com.example.campingontop.house.repository.HouseRepository;
import com.example.campingontop.houseImage.model.HouseImage;
import com.example.campingontop.houseImage.service.HouseImageService;
import com.example.campingontop.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {
    private final HouseRepository houseRepository;
    private final HouseImageService houseImageService;

    public List<GetFindHouseDtoRes> findByLikeCntDesc(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<House> result = houseRepository.findByLikeCntDesc(pageable);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }

    public List<GetFindHouseDtoRes> findByPriceDesc(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<House> result = houseRepository.findByPriceDesc(pageable);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }
    public List<GetFindHouseDtoRes> findByPriceAsc(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<House> result = houseRepository.findByPriceAsc(pageable);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }
    public List<GetFindHouseDtoRes> findByName(Integer page, Integer size, String name){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<House> result = houseRepository.findByName(pageable, name);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }


    public List<GetFindHouseDtoRes> getNearestHouseList(GetHouseListPagingByLocReq req){
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.getNearestHouseList(pageable, req.getLatitude(), req.getLongitude());

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }


    public List<GetFindHouseDtoRes> findByAddress(Integer page, Integer size, String address){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<House> result = houseRepository.findByAddress(pageable, address);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result) {
            List<HouseImage> houseImageList = house.getHouseImageList();
            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }

    public PostCreateHouseDtoRes createHouse(User user, PostCreateHouseDtoReq request, MultipartFile[] uploadFiles) {
        Optional<House> result = houseRepository.findByName(request.getName());
        if (result.isPresent()) {
            throw new HouseException(ErrorCode.DUPLICATED_HOUSE, String.format("숙소 이름: %s", request.getName()));
        }

        House house = House.builder()
                .name(request.getName())
                .content(request.getContent())
                .price(request.getPrice())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .maxUser(request.getMaxUser())
                .hasAirConditioner(request.getHasAirConditioner())
                .hasWashingMachine(request.getHasWashingMachine())
                .hasBed(request.getHasBed())
                .hasHeater(request.getHasHeater())
                .user(user)
                .status(true)
                .likeCnt(0)
                .build();

        house = houseRepository.save(house);
        houseImageService.createHouseImage(house.getId(), uploadFiles);

        PostCreateHouseDtoRes response = PostCreateHouseDtoRes.toDto(house);

        return response;
    }

    @Transactional(readOnly = true)
    public GetFindHouseDtoRes findHouseById(Long houseId) {
        Optional<House> result = houseRepository.findActiveHouse(houseId);
        if (result.isPresent()) {
            House house = result.get();
            List<HouseImage> houseImageList = house.getHouseImageList();

            List<String> filenames = new ArrayList<>();

            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }

    @Transactional(readOnly = true)
    public List<GetFindHouseDtoRes> findHouseList(GetHouseListPagingByLocReq req) {
        Pageable pageable = PageRequest.of(req.getPage()-1, req.getSize());
        Page<House> result = houseRepository.findList(pageable);

        List<GetFindHouseDtoRes> houseList = new ArrayList<>();

        for (House house : result.getContent()) {
            List<HouseImage> houseImageList = house.getHouseImageList();

            List<String> filenames = new ArrayList<>();
            for (HouseImage productImage : houseImageList) {
                String filename = productImage.getFilename();
                filenames.add(filename);
            }

            GetFindHouseDtoRes res = GetFindHouseDtoRes.toDto(house, filenames);
            houseList.add(res);
        }
        return houseList;
    }


    public GetHouseLikeDtoRes addHouseHeart(User user, Long houseId) {
        Optional<House> result = houseRepository.findById(houseId);

        if (result.isPresent()) {
            House house = result.get();
            house.increaseLikeCount();
            house = houseRepository.save(house);

            GetHouseLikeDtoRes res = GetHouseLikeDtoRes.toDto(house);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }


    public PatchUpdateHouseDtoRes updateHouse(User user, PatchUpdateHouseDtoReq req, Long houseId) {
        Optional<House> result = houseRepository.findById(houseId);
        if (result.isPresent()) {
            House house = result.get();

            house.setName(req.getName());
            house.setContent(req.getContent());
            house.setPrice(req.getPrice());
            house.setAddress(req.getAddress());
            house.setLatitude(req.getLatitude());
            house.setLongitude(req.getLongitude());
            house.setMaxUser(req.getMaxUser());
            house.setStatus(req.getIsActive());
            house.setHasAirConditioner(req.getHasAirConditioner());
            house.setHasWashingMachine(req.getHasWashingMachine());
            house.setHasBed(req.getHasBed());
            house.setHasHeater(req.getHasHeater());

            house = houseRepository.save(house);

            PatchUpdateHouseDtoRes res = PatchUpdateHouseDtoRes.toDto(house);
            return res;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }


    public void deleteHouse(User user, Long houseId) {
        Optional<House> result = houseRepository.findActiveHouseForDelete(user.getId(), houseId);
        if (result.isPresent()) {
            House house = result.get();
            house.setStatus(false);
            houseRepository.save(house);
            return;
        }
        throw new HouseException(ErrorCode.HOUSE_NOT_EXIST);
    }
}
