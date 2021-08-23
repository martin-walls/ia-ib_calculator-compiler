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

  public static Token plus() {
    return new Token(Type.PLUS);
  }

  public static Token minus() {
    return new Token(Type.MINUS);
  }

  public static Token mult() {
    return new Token(Type.MULT);
  }

  public static Token cos() {
    return new Token(Type.COS);
  }

  public static Token fact() {
    return new Token(Type.FACT);
  }

  public static Token openBracket() {
    return new Token(Type.OPEN_BRACKET);
  }

  public static Token closeBracket() {
    return new Token(Type.CLOSE_BRACKET);
  }

  public static Token eol() {
    return new Token(Type.EOL);
  }

  public static Token ufloat() {
    return new Token(Type.UFLOAT);
  }
}
