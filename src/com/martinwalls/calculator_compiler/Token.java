package com.martinwalls.calculator_compiler;

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
}
