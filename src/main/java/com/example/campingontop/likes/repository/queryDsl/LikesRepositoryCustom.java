package com.example.campingontop.likes.repository.queryDsl;


import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.user.model.User;

import java.util.List;

public interface LikesRepositoryCustom {
    List<Likes> findLikeList(User user);

    List<Likes> findLikesByUserId(Long userId);
}
