package com.martinwalls.calculator_compiler.tokens;

public class Number extends Token {
  private double value;

  public Number(double value) {
    super(TokenType.NUM);
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
