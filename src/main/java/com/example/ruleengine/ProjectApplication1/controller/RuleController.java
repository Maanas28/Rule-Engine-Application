package com.example.ruleengine.ProjectApplication1.controller;

import com.example.ruleengine.ProjectApplication1.model.Node;
import com.example.ruleengine.ProjectApplication1.model.RequestData;
import com.example.ruleengine.ProjectApplication1.model.Rule;
import com.example.ruleengine.ProjectApplication1.repository.NodeRepository;
import com.example.ruleengine.ProjectApplication1.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rules")
@CrossOrigin(origins = "http://localhost:63342")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private NodeRepository nodeRepository;


    @PostMapping("/create")
    public Node createRule(@RequestBody String ruleString) {
        return ruleService.createRule(ruleString);
    }

    @PostMapping("/combine")
    public ResponseEntity<Node> combineRules(@RequestBody List<String> rules) {
        try {
            Node combinedNode = ruleService.combineRule(rules);
            return ResponseEntity.ok(combinedNode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/evaluate")
    public ResponseEntity<?> evaluateRule(@RequestBody RequestData requestData) {
        try {
            if (requestData.getAstId() == null || requestData.getData() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rule ID and data are required");
            }
            Node root = nodeRepository.findById(Long.valueOf(requestData.getAstId())).orElse(null);
            Boolean result = ruleService.evaluateRule(requestData.getData(),root);
            if (result == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data to evaluate");
            }

            return ResponseEntity.ok(Map.of("result", result));
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error evaluating rule"));
        }
    }

}
