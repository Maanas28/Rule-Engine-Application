package com.example.ruleengine.ProjectApplication1.model;


import jakarta.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String value;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "left_id")
    private Node left;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "right_id")
    private Node right;

    public Node() {}



    public Node(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    // Constructor for operator nodes
    public Node(String type, String value, Node left, Node right) {
        this.value = value;
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public void setLeftId(Long id) {
    }

    public void setRightId(Long id) {
    }
}
