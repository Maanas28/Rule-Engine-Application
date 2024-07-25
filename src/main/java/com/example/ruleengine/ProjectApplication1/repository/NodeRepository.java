package com.example.ruleengine.ProjectApplication1.repository;

import com.example.ruleengine.ProjectApplication1.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}