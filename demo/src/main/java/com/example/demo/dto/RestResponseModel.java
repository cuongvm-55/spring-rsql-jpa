package com.example.demo.dto;

import lombok.Data;

@Data
public class RestResponseModel <T>{
	private T data;
	private PagingData metadata;
}
