package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getUsers();

    public Optional<User> getById(Integer id);

    public void deleteUser(User user);

    public void saveUser(User user);
}
