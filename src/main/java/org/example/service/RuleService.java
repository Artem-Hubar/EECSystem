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
        return deserializer.deserialize(ruleData.getData());
    }

    public List<Rule> getAllRuleData() {
        List<RuleData> ruleData = ruleDataService.getAllRuleData();
        return ruleData.stream().map(this::getRuleByData).toList();
    }
}
