package edu.tongji.webgis.service.impl;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.tongji.webgis.dao.mapper.UserMapper;
import edu.tongji.webgis.model.User;
import edu.tongji.webgis.service.UserService;
import edu.tongji.webgis.utils.MD5Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Override
    public User addUser(String username, String password, User.Role role) throws DataAccessException{
        User user = new User();
        user.setActive(true);
        user.setPassword(MD5Tool.getMd5(password));
        user.setRole(role);
        user.setUsername(username);
        try {
            userMapper.insertUser(user);
        }catch (DataAccessException e){
            throw e;
        }
        return user;
    }
}
