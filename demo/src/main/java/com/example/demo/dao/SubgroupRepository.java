package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.SubGroup;

public interface SubgroupRepository extends JpaRepository<SubGroup, Long>, JpaSpecificationExecutor<SubGroup> {
}

