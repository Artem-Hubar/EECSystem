package org.example.factory;

import org.example.service.postgres.RuleDataRepository;

public class RuleDataRepositoryFactory {
    public static RuleDataRepository getInstance() {
        return new RuleDataRepository();
    }
}
