package com.martinwalls.calculator_compiler.tokens;

public class Token {
  private final TokenType type;

  public Token(TokenType t) {
    this.type = t;
  }

  public TokenType getType() {
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
