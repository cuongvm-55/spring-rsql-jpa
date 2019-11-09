package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.GroupRepository;
import com.example.demo.dto.GroupCreateModel;
import com.example.demo.entity.Group;

@Controller
@RequestMapping("/api/groups")
public class GroupController {
	@Autowired
	private GroupRepository repository;
	
	@PostMapping
	@ResponseBody
	public Group addGroup(@RequestBody GroupCreateModel groupdto) {
		Group g = new Group();
		g.setName(groupdto.getName());
		return repository.save(g);
	}
	
	@GetMapping
	@ResponseBody
	public List<Group> getGroups() {
		return repository.findAll();
	}
}
