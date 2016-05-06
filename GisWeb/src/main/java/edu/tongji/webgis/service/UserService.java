package edu.tongji.webgis.service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.tongji.webgis.model.User;
import org.springframework.dao.DataAccessException;

/**
 * Created by panyan on 15/11/26.
 */
public interface UserService {

    public User findUserByUsername(String username);

    public User addUser(String username, String password, User.Role role, String email , String realName, String phone) throws DataAccessException;

}
