package com.wwh.dao;

import com.wwh.entity.User;
import java.util.List;

public interface UserMapper {
    int insert(User record);

    List<User> selectAll();
}