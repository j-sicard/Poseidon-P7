package com.nnk.springboot.model;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @Column()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column()
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column()
    @Size(min = 8, message = "The size of the password must be of minimum 8 characters")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column()
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @Column()
    @NotBlank(message = "Role is mandatory")
    private String role;

    public UserModel() {
    }

    public UserModel(Integer id, String username, String password, String fullname, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
