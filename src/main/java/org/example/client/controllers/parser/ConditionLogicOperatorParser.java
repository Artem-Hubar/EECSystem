package org.example.client.controllers.parser;

import javafx.scene.control.ChoiceBox;
import org.example.client.controllers.ExpressionsContainerController;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ConditionLogicOperatorParser {
    public @NotNull String parse(ExpressionsContainerController expressionContainer) {
        Optional<String> logicalOperatorOptional = getOperatorFromExpression(expressionContainer);
        return logicalOperatorOptional.orElse("AND");
    }

    private Optional<String> getOperatorFromExpression(ExpressionsContainerController containerController) {
        ChoiceBox<String> choiceBox = containerController.getChoiceBox();
        if (choiceBox != null) {
            return Optional.of(choiceBox.getValue());
        }
        return Optional.empty();
    }
}
