package com.martinwalls.calculator_compiler;

public class UFloat extends Token {
  public UFloat(double value) {
    super(Type.UFLOAT);
    this.value = value;
  }

  public double getValue() {
    return (double) value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  protected boolean valueEquals(Token t) {
    return value.equals(t.value);
  }

  @Override
  protected int valueHashcode() {
    return value.hashCode();
  }
}
