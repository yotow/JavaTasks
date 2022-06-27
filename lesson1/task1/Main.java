package lesson1.task1;

import java.util.function.*;

public class Main {

    public static void main(String[] args) {
        Calculator calc = Calculator.instance.get();
        calc.println.accept(calc.multiply.apply(400000000, 400000000));
        calc.println.accept(calc.plus.apply(Integer.MAX_VALUE, 1));
        calc.println.accept(calc.minus.apply(Integer.MAX_VALUE, 0));
        calc.println.accept(calc.abs.apply(Integer.MIN_VALUE));
        calc.println.accept(calc.divide.apply(1, 1));
        calc.println.accept(calc.isPositive.test(-10) ? 1 : 0);
    }
}

class Calculator {
    public Calculator() {
    }

    static Supplier<Calculator> instance = Calculator::new;

    BinaryOperator<Integer> plus = Integer::sum;
    BinaryOperator<Integer> minus = (a, b) -> a - b;
    BinaryOperator<Integer> multiply = (a, b) -> a * b;
    BinaryOperator<Integer> divide = (a, b) -> a / b;

    UnaryOperator<Integer> pow = x -> x * x;
    UnaryOperator<Integer> abs = x -> {
        if (x == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;  // abs(Integer.MIN_VALUE) > INTEGER.MAX_VALUE ("Overflow to represent absolute value of Integer.MIN_VALUE")
        else
            return (x < 0) ? -x : x;
    };

    Predicate<Integer> isPositive = x -> x > 0;

    Consumer<Integer> println = System.out::println;
}