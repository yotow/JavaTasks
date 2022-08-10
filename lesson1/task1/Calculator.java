package lesson1.task1;

import java.util.function.*;

class Calculator {
    final static Integer ERROR_CODE = Integer.MIN_VALUE;

    public Calculator() {
    }

    static Supplier<Calculator> instance = Calculator::new;

    BinaryOperator<Integer> plus = Integer::sum;
    BinaryOperator<Integer> minus = (a, b) -> a - b;
    BinaryOperator<Integer> multiply = (a, b) -> a * b;
    BinaryOperator<Integer> divide = (a, b) -> b == 0 ? ERROR_CODE : a / b;

    UnaryOperator<Integer> pow = x -> x * x;
    UnaryOperator<Integer> abs = x -> {
        if (x == Integer.MIN_VALUE)
            return ERROR_CODE;  // abs(Integer.MIN_VALUE) > INTEGER.MAX_VALUE ("Overflow to represent absolute value of Integer.MIN_VALUE")
        else
            return (x < 0) ? -x : x;
    };

    Predicate<Integer> isPositive = x -> x > 0;

    Consumer<Integer> println = System.out::println;
}
