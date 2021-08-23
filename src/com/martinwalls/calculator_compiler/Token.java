package com.martinwalls.calculator_compiler;

public class Token {
  public enum Type {
    UFLOAT,
    PLUS,
    MINUS,
    MULT,
    COS,
    FACT,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    EOL;
  }

  private final Type type;

  public Token(Type t) {
    this.type = t;
  }

  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return type.name();
  }

  @Override
  public int hashCode() {
    return type.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Token)) return false;
    Token t = (Token) o;
    return type == t.type;
  }
}
