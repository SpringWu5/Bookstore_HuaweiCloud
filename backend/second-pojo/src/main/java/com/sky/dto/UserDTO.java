package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String phone;

    private String sex;

    @Email
    private String email;

    private String avatar;

    private Long collegeId;

    private Long dormitoryId;


}
