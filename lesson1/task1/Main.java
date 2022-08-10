package lesson1.task1;

import java.util.function.*;

public class Main {
    final static Integer ERROR_CODE = Integer.MIN_VALUE;
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
