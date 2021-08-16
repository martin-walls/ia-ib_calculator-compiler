package com.martinwalls.calculator_compiler;

public class Production {

  final Nonterminal head;
  final Symbol[] body;
  final int index;

  public Production(Nonterminal head, Symbol[] body, int index) {
    this.head = head;
    this.body = new Symbol[body.length];
    for (int i = 0; i < body.length; i++) {
      this.body[i] = body[i];
    }
    this.index = index;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(head.name());
    sb.append(" -> ");
    for (Symbol symbol : body) {
      sb.append(symbol.toString());
      sb.append(" ");
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return index;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Production)) return false;
    Production p = (Production) o;
    return index == p.index;
  }
}