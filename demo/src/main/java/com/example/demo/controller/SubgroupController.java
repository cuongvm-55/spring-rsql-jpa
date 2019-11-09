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
import com.example.demo.dao.SubgroupRepository;
import com.example.demo.dto.SubGroupCreateModel;
import com.example.demo.entity.Group;
import com.example.demo.entity.SubGroup;

@Controller
@RequestMapping("/api/subgroups")
public class SubgroupController {
	@Autowired
	private SubgroupRepository repository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@PostMapping
	@ResponseBody
	public SubGroup addSubgroup(@RequestBody SubGroupCreateModel subgroupdto) {
		Group g = groupRepository.findById(subgroupdto.getGroupId()).orElse(null);
		if (g == null) {
			return null;
		}
		
		SubGroup subgroup = new SubGroup();
		subgroup.setName(subgroupdto.getName());
		subgroup.setGroup(g);
		
		return repository.save(subgroup);
	}
	
	@GetMapping
	@ResponseBody
	public List<SubGroup> getSubgroups() {
		return repository.findAll();
	}
}
