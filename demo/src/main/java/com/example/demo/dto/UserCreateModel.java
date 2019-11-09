package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateModel {
	private String firstName;
    private String lastName;
    private String email;
    private int age;
    
    private Long subgroupId;
}
