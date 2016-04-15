package edu.tongji.webgis.dao.mapper;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.tongji.webgis.dao.annotation.Mapper;
import edu.tongji.webgis.model.User;
import edu.tongji.webgis.utils.MyBatisRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by panyan on 15/11/26.
 */
@Repository(value="UserMapper")
public interface UserMapper {

    public User selectUser(String username);

    public void insertUser(User user) throws DataAccessException;

}
