package com.wannago.user.service;

import com.wannago.user.dao.UserDao;
import com.wannago.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getUsers(){
        return userDao.selectAllUser();
    }

    public String addUser(User newUser){
        Long id = userDao.insertUser(newUser);
        String result = "fail";
        if(id != null){
            newUser.setId(id);
            result = "success";
        }
        return result;
    }
}
