package com.martinwalls.calculator_compiler;

public class UFloat extends Token {
  private final double value;

  public UFloat(double value) {
    super(Type.UFLOAT);
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
    if (!(o instanceof UFloat)) return false;
    UFloat n = (UFloat) o;
    return value == n.value;
  }
}
