package com.example.campingontop.likes.repository.queryDsl;

import com.example.campingontop.likes.model.Likes;
import com.example.campingontop.likes.model.QLikes;
import com.example.campingontop.user.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class LikesRepositoryCustomImpl extends QuerydslRepositorySupport implements LikesRepositoryCustom {
    public LikesRepositoryCustomImpl() {
        super(Likes.class);
    }

    @Override
    public List<Likes> findLikeList(User user) {
        QLikes likes = new QLikes("likes");

        List<Likes> result = from(likes)
                .leftJoin(likes.user).fetchJoin()
                .leftJoin(likes.house).fetchJoin()
                .where(likes.status.eq(true).and(likes.user.id.eq(user.getId())))
                .distinct()
                .fetch().stream().collect(Collectors.toList());

        return result;
    }

    @Override
    public List<Likes> findLikesByUserId(Long userId) {
        QLikes qLikes = QLikes.likes;
        return from(qLikes)
                .where(qLikes.user.id.eq(userId).and(qLikes.status.eq(true)))
                .fetch();
    }

}
