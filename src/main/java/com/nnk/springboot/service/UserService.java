package com.nnk.springboot.service;

import com.nnk.springboot.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<UserModel> getUsers();

    public Optional<UserModel> getById(Integer id);

    public void deleteUser(UserModel user);

    public void saveUser(UserModel user);

    public boolean validatePassword(String password);
}
