package org.example.service;

import junit.framework.TestCase;
import org.example.rule.entity.Rule;
import org.example.rule.RuleSerializerTest;

import java.util.List;

public class RuleServiceTest extends TestCase {
    public void testName() {
        Rule rule = RuleSerializerTest.getRule();
        RuleService ruleService = new RuleService();
        ruleService.saveRule(rule);

        List<Rule> rules =  ruleService.getAllRule();
        System.out.println(rules.getFirst());
        RuleSerializerTest.isRuleEqual(rule, rules.getFirst());
    }
}
