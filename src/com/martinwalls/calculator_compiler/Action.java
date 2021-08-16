package com.martinwalls.calculator_compiler;

public class Action {

  public enum Type {Shift, Reduce, Accept}

  private final Type type;
  private final int toState;

  public Action(Type type, int toState) {
    this.type = type;
    this.toState = toState;
  }

  public static Action shift(int toState) {
    return new Action(Type.Shift, toState);
  }

  public static Action reduce(int toState) {
    return new Action(Type.Reduce, toState);
  }

  public static Action accept() {
    return new Action(Type.Accept, -1);
  }

  public Type getType() {
    return type;
  }

  public int getToState() {
    return toState;
  }
}
