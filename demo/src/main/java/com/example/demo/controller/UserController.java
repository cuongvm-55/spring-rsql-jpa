package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.SubgroupRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.UserSpecification;
import com.example.demo.dto.PagingData;
import com.example.demo.dto.RestResponseModel;
import com.example.demo.dto.UserCreateModel;
import com.example.demo.entity.SubGroup;
import com.example.demo.entity.User;

@Controller
@RequestMapping("/api/users")
public class UserController {
 
    @Autowired
    private UserRepository repo;
    
    @Autowired
    private SubgroupRepository subgroupRepo;
    
    // We will need a JPA EntityManager
    @PersistenceContext
	EntityManager manager;
 
    //localhost:8080/api/users?search=(subgroup.id==12 and (createdAt=ge=20191109))&sort=+firstName&sort=+lastName&size=1&page=1
    @GetMapping
    @ResponseBody
    public RestResponseModel<List<User>> search(@RequestParam(value = "search") String search,
    		@RequestParam(defaultValue = "20") int size,
    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam List<String> sort) {
    	Sort sortObj = Sort.unsorted();
    	if (sort != null) {
    		for (String string : sort) {
				System.out.println(string);
				if (string.startsWith("-")) {
					sortObj = sortObj.and(Sort.by(Sort.Direction.DESC, string.replace("-", "").trim()));
				}
				else {
					sortObj = sortObj.and(Sort.by(Sort.Direction.ASC, string.replace("+", "").trim()));
				}
			}
    	}	
    	Pageable pageable = PageRequest.of(page, size, sortObj);

    	Specification<User> userSpec = new UserSpecification(manager)
    			.getUserSearchSpecification(search);
    	Page<User> userPage = repo.findAll(userSpec, pageable);
    	
    	PagingData pagingData = PagingData.createFromPage(userPage);
    	pagingData.setSearch(search);
    	pagingData.setSort(pageable.getSort().toString());
    	
    	RestResponseModel<List<User>> response = new RestResponseModel<List<User>>();
    	response.setData(userPage.getContent());
    	response.setMetadata(pagingData);
    	
    	return response;
    }
    
    @PostMapping
    @ResponseBody
    public User addUser(@RequestBody UserCreateModel userModel) {
    	SubGroup sg = subgroupRepo.findById(userModel.getSubgroupId()).orElse(null);
    	if (sg == null) {
    		return null;
    	}
    	
    	User user = new User();
    	user.setAge(userModel.getAge());
    	user.setCreatedAt(new Date());
    	user.setEmail(userModel.getEmail());
    	user.setFirstName(userModel.getFirstName());
    	user.setLastName(userModel.getLastName());
    	user.setSubgroup(sg);
        return repo.save(user);
    }
}