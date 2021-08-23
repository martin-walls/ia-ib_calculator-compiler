package com.martinwalls.calculator_compiler;

public class Item {

  private final Production production;
  private final int dotPosition;

  public Item(Production production, int dotPosition) {
    this.production = production;
    this.dotPosition = dotPosition;
  }

  public Production getProduction() {
    return production;
  }

  public int getDotPosition() {
    return dotPosition;
  }

  @Override
  public int hashCode() {
    return production.hashCode() * 13 + dotPosition;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Item)) return false;
    Item i = (Item) o;
    return production.equals(i.production) && dotPosition == i.dotPosition;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(production.getHead().name());
    sb.append(" -> ");
    Symbol[] body = production.getBody();
    for (int i = 0; i < body.length; i++) {
      if (dotPosition == i) {
        sb.append(".");
      }
      sb.append(body[i].toString());
      sb.append(" ");
    }
    if (dotPosition == body.length) {
      sb.append(".");
    }
    return sb.toString();
  }
}
