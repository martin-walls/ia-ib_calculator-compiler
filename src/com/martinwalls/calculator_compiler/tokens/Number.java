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

  @Override
  public int hashCode() {
    return Double.hashCode(value);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Number)) return false;
    Number n = (Number) o;
    return value == n.value;
  }
}
