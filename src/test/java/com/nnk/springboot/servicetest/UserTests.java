package com.nnk.springboot.servicetest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserTests extends AbstractConfigurationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void userTest(){
        User user = new User();
        user.setUsername("UserName");
        user.setFullname("UserFullName");

        // Save
        userService.saveUser(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(user.getUsername(), "UserName");

        // Update
        user.setFullname("UserFullNameUpdate");
        userService.saveUser(user);
        Assert.assertEquals(user.getFullname(), "UserFullNameUpdate");

        // Find
        List<User> listResult = userService.getUsers();
        System.out.println("list size" + listResult.size());
        Assert.assertTrue(listResult.size() > 0);


        // Delete
        Integer id = user.getId();
        userService.deleteUser(user);
        Optional<User> userList = userService.getById(id);
        Assert.assertFalse(userList.isPresent());


    }
}
