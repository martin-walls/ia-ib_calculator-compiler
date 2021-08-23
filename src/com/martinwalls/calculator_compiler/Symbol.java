package com.martinwalls.calculator_compiler;

public class Symbol {

  public enum Type {Terminal, Nonterminal}

  private final Token terminal;
  private final Nonterminal nonterminal;
  private final Type type;

  public Symbol(Token terminal) {
    type = Type.Terminal;
    this.terminal = terminal;
    nonterminal = null;
  }

  public Symbol(Nonterminal nonterminal) {
    type = Type.Nonterminal;
    this.nonterminal = nonterminal;
    terminal = null;
  }

  public Token getTerminal() {
    return terminal;
  }

  public Nonterminal getNonterminal() {
    return nonterminal;
  }

  public Type getType() {
    return type;
  }

  public boolean isTerminal() {
    return type == Type.Terminal;
  }

  public boolean isNonterminal() {
    return type == Type.Nonterminal;
  }

  @Override
  public String toString() {
    if (type == Type.Terminal) {
      return terminal.toString();
    } else {
      return nonterminal.name();
    }
  }

  @Override
  public int hashCode() {
    return (terminal != null ? terminal.hashCode() : 0)
        + (nonterminal != null ? nonterminal.hashCode() : 0)
        + type.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Symbol)) return false;
    Symbol s = (Symbol) o;
    if (s.type != type) return false;
    if (type == Type.Terminal) {
      return terminal.equals(s.terminal);
    } else {
      return nonterminal == s.nonterminal;
    }
  }
}
