package com.example.campingontop.user.repository.queryDsl;


import com.example.campingontop.user.model.response.GetFindUserDtoRes;

import java.util.List;

public interface UserRepositoryCustom {
    List<GetFindUserDtoRes> findActiveUserList();
}
