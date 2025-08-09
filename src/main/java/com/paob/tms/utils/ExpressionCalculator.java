package com.paob.tms.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

@Slf4j
public class ExpressionCalculator {

    /**
     * 计算字符串表达式
     * @param expression 数学表达式字符串
     * @return 计算结果
     */
    public static BigDecimal calculate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("表达式不能为空");
        }

        // 移除所有空格
        expression = expression.replaceAll("\\s+", "");

        Stack<BigDecimal> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // 处理数字
                StringBuilder num = new StringBuilder();
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i++));
                }
                i--;
                numbers.push(new BigDecimal(num.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }
        BigDecimal bigDecimal = numbers.pop().setScale(2, RoundingMode.HALF_UP);
        log.info("expression={}, numbers={}",expression, bigDecimal);

        return bigDecimal;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static BigDecimal applyOperator(char operator, BigDecimal b, BigDecimal a) {
        switch (operator) {
            case '+':
                return a.add(b);
            case '-':
                return a.subtract(b);
            case '*':
                return a.multiply(b);
            case '/':
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("除数不能为零");
                }
                return a.divide(b, 10, RoundingMode.HALF_UP);
            default:
                throw new IllegalArgumentException("无效的运算符: " + operator);
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        try {
            // 测试用例
            String[] expressions = {
                    "100000 -(900*9.5 +1000/2)",
                    "1000 + 2000 * 3",
                    "(100 + 200) * 3",
                    "1000 / 3"
            };

            for (String expr : expressions) {
                BigDecimal result = calculate(expr);
                System.out.println(expr + " = " + result);
            }

        } catch (Exception e) {
            System.out.println("计算错误: " + e.getMessage());
        }
    }
}