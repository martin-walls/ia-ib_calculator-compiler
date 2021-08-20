package com.martinwalls.calculator_compiler;

public class Action {

  public enum Type {Shift, Reduce, Accept}

  private final Type type;
  private final int value;

  public Action(Type type, int value) {
    this.type = type;
    this.value = value;
  }

  public static Action shift(int toState) {
    return new Action(Type.Shift, toState);
  }

  public static Action reduce(int productionNo) {
    return new Action(Type.Reduce, productionNo);
  }

  public static Action accept() {
    return new Action(Type.Accept, -1);
  }

  public Type getType() {
    return type;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    if (type == Type.Shift) {
      return "Shift " + value;
    } else if (type == Type.Reduce) {
      return "Reduce " + value;
    } else {
      return "Accept";
    }
  }
}
