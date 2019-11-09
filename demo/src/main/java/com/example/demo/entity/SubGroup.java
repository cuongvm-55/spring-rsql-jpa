package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subgroup")
public class SubGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String name;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subgroup")
	//private List<User> Users = new ArrayList<User>();
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
}
