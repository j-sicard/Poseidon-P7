package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }

    public Optional<UserModel> getById(Integer id){
        return userRepository.findById(id);
    }

    public void deleteUser(UserModel user){
        userRepository.delete(user);
    }

    public void saveUser(UserModel user){
        userRepository.save(user);
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
