package com.example.campingontop.user.repository.queryDsl;

import com.example.campingontop.user.model.QUser;
import com.example.campingontop.user.model.User;
import com.example.campingontop.user.model.response.GetFindUserDtoRes;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    public UserRepositoryCustomImpl() {
        super(User.class);
    }

    @Override
    public List<GetFindUserDtoRes> findActiveUserList() {
        QUser user = QUser.user;

        List<User> users = from(user)
                .where(user.status.eq(true))
                .fetch();

        return users.stream()
                .map(GetFindUserDtoRes::toDto)
                .collect(Collectors.toList());
    }
}
