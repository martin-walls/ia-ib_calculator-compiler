package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;

public class Symbol {

  public enum Type {Terminal, Nonterminal}

  final Token terminal;
  final Nonterminal nonterminal;
  final Type type;

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
