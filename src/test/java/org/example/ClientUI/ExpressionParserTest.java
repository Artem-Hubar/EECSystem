package org.example.ClientUI;

import org.example.client.controllers.parser.ExpressionParser;
import org.example.rule.entity.Expression;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionParserTest {
    public class Person {
        private int age;

        public Person(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
    @Test
    public void testComplexExpressionWithObjects() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(5);
        Person person3 = new Person(3);
        Person person4 = new Person(2);

        // Список выражений
        List<Object[]> expressionList = Arrays.asList(
                new Object[]{person1, "getAge", "+"}, // person1.getAge() +
                new Object[]{person2, "getAge", "-"}, // person2.getAge() -
                new Object[]{person3, "getAge", "/"}, // person3.getAge() /
                new Object[]{person4, "getAge", "=="}, // person4.getAge()
                new Object[]{6.0, null, null} // person4.getAge()
        );

        // Строим выражение
        ExpressionParser parser = new ExpressionParser();
        Expression parsedExpression = parser.parseExpressionList(expressionList);

        // Вычисляем результат
        Object result = parsedExpression.evaluate();
        System.out.println("Result: " + result); // ((10 + 5) - 3) / 2 = 6.0
        assertEquals(true, result);
    }
}
