package com.example.ruleengine.ProjectApplication1.service;

import com.example.ruleengine.ProjectApplication1.model.Node;
import com.example.ruleengine.ProjectApplication1.model.Rule;
import com.example.ruleengine.ProjectApplication1.repository.NodeRepository;
import com.example.ruleengine.ProjectApplication1.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class RuleService {

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private RuleRepository ruleRepository;


    public  Node createRule(String ruleString) {
        // Parse ruleString to create AST
        Node root = createAST(ruleString);
        ruleRepository.save(new Rule(ruleString,root));
        return nodeRepository.save(root);
    }

    public  Node createAST(String ruleString) {
        List<String> tokens = tokenize(ruleString);
        return parse(tokens);
    }

    private  List<String> tokenize(String ruleString) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < ruleString.length(); i++) {
            char c = ruleString.charAt(i);

            if (c == ' ') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else if (c == '(' || c == ')' || c == '>' || c == '<' || c == '=') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(Character.toString(c));
            } else {
                currentToken.append(c);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    private  Node parse(List<String> tokens) {
        Stack<Node> stack = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    String operator = operators.pop();
                    Node right = stack.pop();
                    Node left = stack.pop();
                    stack.push(new Node("operator", operator, left, right));
                }
                operators.pop(); // Remove '('
            } else if (token.equals("AND") || token.equals("OR")) {
                while (!operators.isEmpty() && !operators.peek().equals("(") &&
                        getPrecedence(operators.peek()) >= getPrecedence(token)) {
                    String operator = operators.pop();
                    Node right = stack.pop();
                    Node left = stack.pop();
                    stack.push(new Node("operator", operator, left, right));
                }
                operators.push(token);
            } else if (token.equals(">") || token.equals("<") || token.equals("=")) {
                Node operand1 = stack.pop();
                String operand2 = tokens.get(++i); // Skip the next token
                stack.push(new Node("operand", operand1.getValue() + " " + token + " " + operand2));
            } else {
                stack.push(new Node("operand", token));
            }
        }

        while (!operators.isEmpty()) {
            String operator = operators.pop();
            Node right = stack.pop();
            Node left = (stack.isEmpty() ? null : stack.pop());
            stack.push(new Node("operator", operator, left, right));
        }

        return stack.isEmpty() ? null : stack.peek();
    }

    private  int getPrecedence(String operator) {
        if (operator.equals("AND")) {
            return 1;
        } else if (operator.equals("OR")) {
            return 0;
        } else {
            return -1;
        }
    }


    public  Node combineRule(List<String> rules) {
        Map<String, Integer> freqMap = new HashMap<>();

        // Count frequency of operators
        for (String rule : rules) {
            Node ast = createRule(rule);
            traverseAST(ast, (node) -> {
                if ("operator".equals(node.getType())) {
                    freqMap.put(node.getValue(), freqMap.getOrDefault(node.getValue(), 0) + 1);
                }
            });
        }

        // Sort operators by frequency
        List<Map.Entry<String, Integer>> sortedOperators = new ArrayList<>(freqMap.entrySet());
        sortedOperators.sort((a, b) -> b.getValue() - a.getValue());

        Node root = null;
        for (String rule : rules) {
            Node ast = createRule(rule);
            if (root == null) {
                root = ast;
            } else {
                root = combineASTs(root, ast, sortedOperators.get(0).getKey());
            }
        }

        return root;
    }

public  void traverseAST(Node node, Consumer<Node> callback) {
    if (node == null) return;
    callback.accept(node);
    traverseAST(node.getLeft(), callback);
    traverseAST(node.getRight(), callback);
}

    public  Node combineASTs(Node left, Node right, String operator) {
        Node root = new Node("operator", operator);
        root.setLeft(left);
        root.setRight(right);
        return root;
    }


    public  boolean evaluateRule(Map<String, Object> data, Node ast) {
        if ("operand".equals(ast.getType())) {
            String[] parts = ast.getValue().split(" ");
            String key = parts[0];
            String operator = parts[1];
            String value = parts[2].replaceAll("'|\"", "");
            Object userData = data.get(key);

            // Assuming userData is a type that supports comparison (like Integer or Double)
            switch (operator) {
                case ">":
                    return Double.compare((Double) userData, Double.parseDouble(value)) > 0;
                case "<":
                    return Double.compare((Double) userData, Double.parseDouble(value)) < 0;
                case "=":
                    return userData.toString().equals(value);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
            }
        } else if ("operator".equals(ast.getType())) {
            boolean leftResult = evaluateRule(data, ast.getLeft());
            boolean rightResult = evaluateRule(data, ast.getRight());
            switch (ast.getValue()) {
                case "AND":
                    return leftResult && rightResult;
                case "OR":
                    return leftResult || rightResult;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + ast.getValue());
            }
        } else {
            throw new IllegalArgumentException("Unknown node type: " + ast.getType());
        }
    }
}