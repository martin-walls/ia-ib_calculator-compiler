package com.martinwalls.calculator_compiler;

public class Production {

  private final Nonterminal head;
  private final Symbol[] body;
  private final int index;

  public Production(Nonterminal head, Symbol[] body, int index) {
    this.head = head;
    this.body = new Symbol[body.length];
    for (int i = 0; i < body.length; i++) {
      this.body[i] = body[i];
    }
    this.index = index;
  }

  public Nonterminal getHead() {
    return head;
  }

  public Symbol[] getBody() {
    return body;
  }

  public int getIndex() {
    return index;
  }

  public int getLength() {
    return body.length;
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
