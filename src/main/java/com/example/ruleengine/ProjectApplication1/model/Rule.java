package com.example.ruleengine.ProjectApplication1.model;


import jakarta.persistence.*;

@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rule;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ast_id")
    private Node ast;


    public Rule() {}

    public Rule(String rule, Node ast) {
        this.rule = rule;
        this.ast = ast;
    }

    public Long getId() {
        return id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Node getAst() {
        return ast;
    }

    public void setAst(Node ast) {
        this.ast = ast;
    }
}
