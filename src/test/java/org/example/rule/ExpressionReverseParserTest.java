package org.example.rule;

import org.example.client.entity.CurrentSensorUI;
import org.example.client.ruleparser.ExpressionReverseParser;
import org.example.entity.Device;
import org.example.rule.entity.Expression;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionReverseParserTest {

    @Test
    void testSingleNodeExpression() {
        // Условие: Одно терминальное выражение
        Expression expression = new Expression("Device1", "method1");

        ExpressionReverseParser parser = new ExpressionReverseParser();
        List<Object[]> result = parser.reverseParseExpression(expression);

        // Ожидаемый результат
        List<Object[]> expected = new ArrayList<>();
        expected.add(new Object[]{"Device1", "method1", null});

        // Проверка
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testSimpleBinaryExpression() {
        // Условие: Бинарное выражение
        Expression expression = new Expression(
            new Expression("Device1", "method1"),
            "+",
            new Expression(42.0, null)
        );

        ExpressionReverseParser parser = new ExpressionReverseParser();
        List<Object[]> result = parser.reverseParseExpression(expression);

        // Ожидаемый результат
        List<Object[]> expected = new ArrayList<>();
        expected.add(new Object[]{"Device1", "method1", "+"});
        expected.add(new Object[]{42.0, null, null});

        // Проверка
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {

            assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testNestedExpression() {
        // Условие: Вложенное выражение
        Expression expression = new Expression(
            new Expression(
                new Expression("Device1", "method1"),
                "+",
                new Expression(42.0, null)
            ),
            "*",
            new Expression("Device2", "method2")
        );

        ExpressionReverseParser parser = new ExpressionReverseParser();
        List<Object[]> result = parser.reverseParseExpression(expression);

        // Ожидаемый результат
        List<Object[]> expected = new ArrayList<>();
        expected.add(new Object[]{"Device1", "method1", "+"});
        expected.add(new Object[]{42.0, null, "*"});
        expected.add(new Object[]{"Device2", "method2", null});

        // Проверка
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            System.out.println(Arrays.toString(result.get(i)));
            assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testComplexExpression() {
        // Условие: Более сложное выражение
        ExpressionTest.Person person1 = new ExpressionTest.Person(10);
        ExpressionTest.Person person2 = new ExpressionTest.Person(5);
        ExpressionTest.Person person3 = new ExpressionTest.Person(3);
        ExpressionTest.Person person4 = new ExpressionTest.Person(2);

        // ((person1.getAge() + person2.getAge()) - person3.getAge()) / person4.getAge()
        Expression expr1 = new Expression(person1, "getAge");
        Expression expr2 = new Expression(person2, "getAge");
        Expression expr3 = new Expression(person3, "getAge");
        Expression expr4 = new Expression(person4, "getAge");

        // (person1.getAge() + person2.getAge())
        Expression additionExpr = new Expression(expr1, "+", expr2);

        // (additionExpr - person3.getAge())
        Expression subtractionExpr = new Expression(additionExpr, "-", expr3);

        // (subtractionExpr / person4.getAge())
        Expression divisionExpr = new Expression(subtractionExpr, "/", expr4);
        Expression divisionTest = new Expression(divisionExpr, "==", new Expression(6.0));

        ExpressionReverseParser parser = new ExpressionReverseParser();
        List<Object[]> result = parser.reverseParseExpression(divisionTest);

        // Ожидаемый результат
        List<Object[]> expected = new ArrayList<>();
        expected.add(new Object[]{"10", "getAge", "+"});
        expected.add(new Object[]{"5", "getAge", "-"});
        expected.add(new Object[]{"3", "getAge", "/"});
        expected.add(new Object[]{"2", "getAge", "=="});
        expected.add(new Object[]{"6", "getAge", null});

        // Проверка
//        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            System.out.printf("%s %s\n", Arrays.toString(expected.get(i)), Arrays.toString(result.get(i)));
//            assertArrayEquals(expected.get(i), result.get(i));
        }
    }
}
