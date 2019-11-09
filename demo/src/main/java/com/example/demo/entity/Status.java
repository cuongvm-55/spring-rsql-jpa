package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum Status {
	VALIDATED("VALIDATED"), DELETED("DELETED"), BLOCKED("BLOCKED");
	private String name;
	
	private Status(String name) {
		this.name = name;
	}
}
