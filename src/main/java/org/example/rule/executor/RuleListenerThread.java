package org.example.rule.executor;

import org.example.rule.entity.Rule;
import org.example.service.RuleService;

public class RuleListenerThread extends Thread{

    private Rule rule;

    public RuleListenerThread(Rule rule) {
        this.rule = rule;
    }


    @Override
    public void run() {
        while (true){
            RuleExecutor ruleExecutor = new RuleExecutor(rule);
            ruleExecutor.execute();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            RuleService ruleService =new RuleService();
            rule = ruleService.getRuleDataById(rule.getId());
        }

    }
}
