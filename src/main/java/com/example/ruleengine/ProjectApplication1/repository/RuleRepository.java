package com.example.ruleengine.ProjectApplication1.repository;

import com.example.ruleengine.ProjectApplication1.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
