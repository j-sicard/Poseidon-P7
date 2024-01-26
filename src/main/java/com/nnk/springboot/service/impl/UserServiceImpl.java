package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

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
}
