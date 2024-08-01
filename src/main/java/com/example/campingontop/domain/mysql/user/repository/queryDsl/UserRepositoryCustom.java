package com.example.campingontop.domain.mysql.user.repository.queryDsl;


import com.example.campingontop.domain.mysql.user.model.response.GetFindUserDtoRes;

import java.util.List;

public interface UserRepositoryCustom {
    List<GetFindUserDtoRes> findActiveUserList();
}
