package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 
    @GetMapping
    @ResponseBody
    public RestResponseModel<List<User>> search(@RequestParam(value = "search") String search,
    							Pageable pageable) {
    	/*
    	// Create criteria and from 
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
    	CriteriaQuery<User> criteria = builder.createQuery(User.class);
    	Root<User> root = criteria.from(User.class);

    	// Create the JPA Predicate Visitor
    	RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<User>().defineRoot(root);

    	// Parse a RSQL into a Node
    	Node rootNode = new RSQLParser().parse(search);

    	// Visit the node to retrieve CriteriaQuery
    	Predicate predicate = rootNode.accept(visitor, manager);

    	// Use generated predicate as you like
    	//criteria.where(predicate);
    	*/
    	//TypedQuery<User> query = manager.createQuery(criteria);
        //return query.getResultList();
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