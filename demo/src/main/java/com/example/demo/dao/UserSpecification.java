package com.example.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.entity.User;
import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class UserSpecification {
	private EntityManager entityManager;
	
	public UserSpecification(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Specification<User> getUserSearchSpecification(String search) {
		return new Specification<User>() {
			private static final long serialVersionUID = -6727025419491153701L;

			@Override
		    public Predicate toPredicate(Root<User> root,
		                                 CriteriaQuery<?> query,
		                                 CriteriaBuilder criteriaBuilder) {
		    	// Create the JPA Predicate Visitor
		    	RSQLVisitor<Predicate, EntityManager> visitor = new JpaPredicateVisitor<User>().defineRoot(root);
		    	// Parse a RSQL into a Node
		    	Node rootNode = new RSQLParser().parse(search);
		    	// Visit the node to retrieve CriteriaQuery
		    	return rootNode.accept(visitor, entityManager);
		    }
		};
	}
}