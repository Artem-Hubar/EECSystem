package org.example.service.postgres;

import org.example.factory.RuleDataRepositoryFactory;
import org.example.service.postgres.entity.RuleData;

import java.util.List;

public class RuleDataService {
    RuleDataRepository ruleDataRepository;

    public RuleDataService() {
        this.ruleDataRepository = RuleDataRepositoryFactory.getInstance();
    }

    public void saveRuleData(String jsonData) {
        RuleData ruleData = new RuleData();
        ruleData.setData(jsonData);
        ruleDataRepository.save(ruleData);
    }

    public RuleData getRuleDataById(Long id) {
        return ruleDataRepository.getById(id);
    }

    public List<RuleData> getAllRuleData() {
        return ruleDataRepository.getAll();
    }
}
