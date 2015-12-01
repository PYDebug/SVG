package edu.tongji.webgis.service;

import edu.tongji.webgis.model.User;

/**
 * Created by panyan on 15/11/26.
 */
public interface UserService {

    public User findUserByUsername(String username);

}
