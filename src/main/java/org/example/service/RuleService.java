package org.example.service;

import org.example.rule.entity.Rule;
import org.example.rule.serializer.RuleDeserializer;
import org.example.rule.serializer.RuleSerializer;
import org.example.service.postgres.entity.RuleData;
import org.example.service.postgres.RuleDataService;

import java.util.List;

public class RuleService {
    RuleDataService ruleDataService;

    public RuleService() {
        this.ruleDataService = new RuleDataService();
    }

    public void saveRule(Rule rule) {
        RuleSerializer ruleSerializer = new RuleSerializer();
        String jsonData = ruleSerializer.serializeRule(rule);
        ruleDataService.saveRuleData(jsonData);
    }

    public Rule getRuleDataById(Long id) {
        RuleData ruleData = ruleDataService.getRuleDataById(id);
        return getRuleByData(ruleData);
    }

    public Rule getRuleByData(RuleData ruleData) {
        RuleDeserializer deserializer = new RuleDeserializer();
        Rule rule = deserializer.deserialize(ruleData.getData());
        rule.setId(ruleData.getId());
        rule.setTimeStamp(ruleData.getCreatedAt());
        return rule;
    }

    public List<Rule> getAllRule() {
        List<RuleData> ruleData = ruleDataService.getAllRuleData();
        return ruleData.stream()
                .map(this::getRuleByData)
                .toList();
    }
}
