package org.example.rule;

import org.example.rule.entity.ConditionWithOperator;
import org.example.rule.entity.Expression;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {
    public static class Person {
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
    public void testAdditionWithObjects() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(5);

        // Создаем выражение: person1.getAge() + person2.getAge()
        Expression expr1 = new Expression(person1, "getAge");  // person1.getAge()
        Expression expr2 = new Expression(person2, "getAge");  // person2.getAge()
        Expression addition = new Expression(expr1, "+", expr2);  // (person1.getAge() + person2.getAge())

        // Проверяем результат: (10 + 5)
        assertEquals(15.0, addition.evaluate());
    }

    @Test
    public void testComplexExpressionWithObjects() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(5);
        Person person3 = new Person(3);
        Person person4 = new Person(2);

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
        Expression divisionTest = new Expression(subtractionExpr, "==", new Expression(6.0));

        // Выводим результат: ((10 + 5) - 3) / 2 = 6.0
        assertEquals(true, divisionTest.evaluate());
    }

    @Test
    public void testMultiplicationWithObjectMethod() {
        // Создаем объекты Person
        Person person1 = new Person(4);
        Person person2 = new Person(5);

        // Создаем выражение: person1.getAge() * person2.getAge()
        Expression expr1 = new Expression(person1, "getAge");  // person1.getAge()
        Expression expr2 = new Expression(person2, "getAge");  // person2.getAge()
        Expression multiplication = new Expression(expr1, "*", expr2);  // (person1.getAge() * person2.getAge())

        // Проверяем результат: (4 * 5)
        assertEquals(20.0, multiplication.evaluate());
    }

    @Test
    public void testNestedExpressionWithObjects() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(5);
        Person person3 = new Person(2);

        // Создаем выражение: (person1.getAge() + person2.getAge()) * person3.getAge()
        Expression expr1 = new Expression(person1, "getAge");
        Expression expr2 = new Expression(person2, "getAge");
        Expression expr3 = new Expression(person3, "getAge");

        // (person1.getAge() + person2.getAge())
        Expression additionExpr = new Expression(expr1, "+", expr2);

        // (additionExpr * person3.getAge())
        Expression multiplicationExpr = new Expression(additionExpr, "*", expr3);

        // Выводим результат: ((10 + 5) * 2) = 30.0
        assertEquals(30.0, multiplicationExpr.evaluate());
    }

    @Test
    public void testDivisionByZeroWithObject() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(0);

        // Создаем выражение: person1.getAge() / person2.getAge()
        Expression expr1 = new Expression(person1, "getAge");
        Expression expr2 = new Expression(person2, "getAge");
        Expression division = new Expression(expr1, "/", expr2);  // (person1.getAge() / person2.getAge())

        // Проверяем результат: 10 / 0 (должен вернуть NaN)
        assertEquals(Double.NaN, division.evaluate());
    }

    @Test
    public void testComplexExpressionWithMethods() {
        // Создаем объекты Person
        Person person1 = new Person(10);
        Person person2 = new Person(5);
        Person person3 = new Person(3);
        Person person4 = new Person(2);

        // Создаем выражение: ((person1.getAge() + person2.getAge()) - person3.getAge()) / person4.getAge()
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

        // Выводим результат: ((10 + 5) - 3) / 2 = 6.0
        assertEquals(6.0, divisionExpr.evaluate());
    }
}
