package edu.tongji.webgis.service.impl;

import edu.tongji.webgis.dao.mapper.UserMapper;
import edu.tongji.webgis.model.User;
import edu.tongji.webgis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by panyan on 15/11/26.
 */
@Service("UserService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        User user =  userMapper.selectUser(username);
        return user;
    }
}
