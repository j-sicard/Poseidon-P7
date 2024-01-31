package com.nnk.springboot.service;

import com.nnk.springboot.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<UserModel> getUsers();

    public Optional<UserModel> getById(Integer id);

    public void deleteUser(UserModel user);

    public void saveUser(UserModel user);

    /*private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
        import java.util.regex.Pattern;
    }*/
}
