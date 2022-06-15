package L1T1;

import java.util.function.*;

public class Main {

    public static void main(String[] args) {
        Calculator calc = Calculator.instance.get();
        calc.println.accept(calc.plus.apply(1, 1));
        calc.println.accept(calc.abs.apply(0));
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
    UnaryOperator<Integer> abs = x -> x > 0 ? x : x * -1;   //ok

    Predicate<Integer> isPositive = x -> x > 0;

    Consumer<Integer> println = System.out::println;
}