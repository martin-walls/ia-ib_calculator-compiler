package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;

public class Symbol {

  enum Type {Terminal, Nonterminal}

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
}
