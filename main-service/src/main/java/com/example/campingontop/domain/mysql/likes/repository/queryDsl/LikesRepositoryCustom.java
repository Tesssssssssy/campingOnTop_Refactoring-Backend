package com.example.campingontop.domain.mysql.likes.repository.queryDsl;


import com.example.campingontop.domain.mysql.likes.model.Likes;
import com.example.campingontop.domain.mysql.user.model.User;

import java.util.List;

public interface LikesRepositoryCustom {
    List<Likes> findLikeList(User user);

    List<Likes> findLikesByUserId(Long userId);
}
