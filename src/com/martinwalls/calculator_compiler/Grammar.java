package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;
import com.martinwalls.calculator_compiler.tokens.TokenType;

public class Grammar {

  public static Production[] productions = {
      production0(),
      production1(),
      production2(),
      production3(),
      production4(),
      production5(),
      production6(),
      production7(),
      production8(),
      production9(),
      production10(),
      production11(),
      production12()
  };

  private static Production production0() {
    Nonterminal head = Nonterminal.Expr_aug;
    Symbol[] body = {new Symbol(Nonterminal.Expr)};
    return new Production(head, body, 0);
  }

  private static Production production1() {
    Nonterminal head = Nonterminal.Expr;
    Symbol[] body = {
        new Symbol(Nonterminal.Expr),
        new Symbol(new Token(TokenType.PLUS)),
        new Symbol(Nonterminal.C)
    };
    return new Production(head, body, 1);
  }

  private static Production production2() {
    Nonterminal head = Nonterminal.Expr;
    Symbol[] body = {new Symbol(Nonterminal.C)};
    return new Production(head, body, 2);
  }

  private static Production production3() {
    Nonterminal head = Nonterminal.C;
    Symbol[] body = {
        new Symbol(Nonterminal.C),
        new Symbol(new Token(TokenType.MINUS)),
        new Symbol(Nonterminal.D)
    };
    return new Production(head, body, 3);
  }

  private static Production production4() {
    Nonterminal head = Nonterminal.C;
    Symbol[] body = {new Symbol(Nonterminal.D)};
    return new Production(head, body, 4);
  }

  private static Production production5() {
    Nonterminal head = Nonterminal.D;
    Symbol[] body = {
        new Symbol(Nonterminal.E),
        new Symbol(new Token(TokenType.MULT)),
        new Symbol(Nonterminal.D)
    };
    return new Production(head, body, 5);
  }

  private static Production production6() {
    Nonterminal head = Nonterminal.D;
    Symbol[] body = {new Symbol(Nonterminal.E)};
    return new Production(head, body, 6);
  }

  private static Production production7() {
    Nonterminal head = Nonterminal.E;
    Symbol[] body = {
        new Symbol(new Token(TokenType.COS)),
        new Symbol(Nonterminal.F)
    };
    return new Production(head, body, 7);
  }

  private static Production production8() {
    Nonterminal head = Nonterminal.E;
    Symbol[] body = {new Symbol(Nonterminal.F)};
    return new Production(head, body, 8);
  }

  private static Production production9() {
    Nonterminal head = Nonterminal.F;
    Symbol[] body = {
        new Symbol(Nonterminal.G),
        new Symbol(new Token(TokenType.FACT))
    };
    return new Production(head, body, 9);
  }

  private static Production production10() {
    Nonterminal head = Nonterminal.F;
    Symbol[] body = {new Symbol(Nonterminal.G)};
    return new Production(head, body, 10);
  }

  private static Production production11() {
    Nonterminal head = Nonterminal.G;
    Symbol[] body = {new Symbol(new Token(TokenType.NUM))};
    return new Production(head, body, 11);
  }

  private static Production production12() {
    Nonterminal head = Nonterminal.G;
    Symbol[] body = {
        new Symbol(new Token(TokenType.OPEN_BRACKET)),
        new Symbol(Nonterminal.Expr),
        new Symbol(new Token(TokenType.CLOSE_BRACKET))
    };
    return new Production(head, body, 12);
  }
}
