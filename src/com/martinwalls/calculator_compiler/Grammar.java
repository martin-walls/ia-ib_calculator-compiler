package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;

import java.util.HashMap;
import java.util.Map;

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

  private static Production production0() {           // [0] expr' -> expr
    Nonterminal head = Nonterminal.Expr_aug;
    Symbol[] body = {new Symbol(Nonterminal.Expr)};
    return new Production(head, body, 0);
  }

  private static Production production1() {           // [1] expr -> expr + C
    Nonterminal head = Nonterminal.Expr;
    Symbol[] body = {
        new Symbol(Nonterminal.Expr),
        new Symbol(new Token(Token.Type.PLUS)),
        new Symbol(Nonterminal.C)
    };
    return new Production(head, body, 1);
  }

  private static Production production2() {           // [2] expr -> C
    Nonterminal head = Nonterminal.Expr;
    Symbol[] body = {new Symbol(Nonterminal.C)};
    return new Production(head, body, 2);
  }

  private static Production production3() {           // [3] C -> C - D
    Nonterminal head = Nonterminal.C;
    Symbol[] body = {
        new Symbol(Nonterminal.C),
        new Symbol(new Token(Token.Type.MINUS)),
        new Symbol(Nonterminal.D)
    };
    return new Production(head, body, 3);
  }

  private static Production production4() {           // [4] C -> D
    Nonterminal head = Nonterminal.C;
    Symbol[] body = {new Symbol(Nonterminal.D)};
    return new Production(head, body, 4);
  }

  private static Production production5() {           // [5] D -> E * D
    Nonterminal head = Nonterminal.D;
    Symbol[] body = {
        new Symbol(Nonterminal.E),
        new Symbol(new Token(Token.Type.MULT)),
        new Symbol(Nonterminal.D)
    };
    return new Production(head, body, 5);
  }

  private static Production production6() {           // [6] D -> E
    Nonterminal head = Nonterminal.D;
    Symbol[] body = {new Symbol(Nonterminal.E)};
    return new Production(head, body, 6);
  }

  private static Production production7() {           // [7] E -> cos F
    Nonterminal head = Nonterminal.E;
    Symbol[] body = {
        new Symbol(new Token(Token.Type.COS)),
        new Symbol(Nonterminal.F)
    };
    return new Production(head, body, 7);
  }

  private static Production production8() {           // [8] E -> F
    Nonterminal head = Nonterminal.E;
    Symbol[] body = {new Symbol(Nonterminal.F)};
    return new Production(head, body, 8);
  }

  private static Production production9() {           // [9] F -> G !
    Nonterminal head = Nonterminal.F;
    Symbol[] body = {
        new Symbol(Nonterminal.G),
        new Symbol(new Token(Token.Type.FACT))
    };
    return new Production(head, body, 9);
  }

  private static Production production10() {          // [10] F -> G
    Nonterminal head = Nonterminal.F;
    Symbol[] body = {new Symbol(Nonterminal.G)};
    return new Production(head, body, 10);
  }

  private static Production production11() {          // [11] G -> NUM
    Nonterminal head = Nonterminal.G;
    Symbol[] body = {new Symbol(new Token(Token.Type.NUM))};
    return new Production(head, body, 11);
  }

  private static Production production12() {          // [12] G -> ( expr )
    Nonterminal head = Nonterminal.G;
    Symbol[] body = {
        new Symbol(new Token(Token.Type.OPEN_BRACKET)),
        new Symbol(Nonterminal.Expr),
        new Symbol(new Token(Token.Type.CLOSE_BRACKET))
    };
    return new Production(head, body, 12);
  }



  public static Map<Integer, Map<Token.Type, Action>> actionTable = initialiseActionTable();

  private static Map<Integer, Map<Token.Type, Action>> initialiseActionTable() {
    Map<Integer, Map<Token.Type, Action>> table = new HashMap<>();

    Map<Token.Type, Action> row0 = new HashMap<>();
    row0.put(Token.Type.NUM, Action.shift(8));
    row0.put(Token.Type.COS, Action.shift(5));
    row0.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(0, row0);

    Map<Token.Type, Action> row1 = new HashMap<>();
    row1.put(Token.Type.PLUS, Action.shift(10));
    row1.put(Token.Type.EOL, Action.accept());
    table.put(1, row1);

    Map<Token.Type, Action> row2 = new HashMap<>();
    row2.put(Token.Type.MINUS, Action.shift(11));
    row2.put(Token.Type.PLUS, Action.reduce(2));
    row2.put(Token.Type.MULT, Action.reduce(2));
    row2.put(Token.Type.FACT, Action.reduce(2));
    row2.put(Token.Type.CLOSE_BRACKET, Action.reduce(2));
    row2.put(Token.Type.EOL, Action.reduce(2));
    table.put(2, row2);

    Map<Token.Type, Action> row3 = new HashMap<>();
    row3.put(Token.Type.MINUS, Action.reduce(4));
    row3.put(Token.Type.PLUS, Action.reduce(4));
    row3.put(Token.Type.MULT, Action.reduce(4));
    row3.put(Token.Type.FACT, Action.reduce(4));
    row3.put(Token.Type.CLOSE_BRACKET, Action.reduce(4));
    row3.put(Token.Type.EOL, Action.reduce(4));
    table.put(3, row3);

    Map<Token.Type, Action> row4 = new HashMap<>();
    row4.put(Token.Type.MINUS, Action.reduce(6));
    row4.put(Token.Type.PLUS, Action.reduce(6));
    row4.put(Token.Type.MULT, Action.shift(12));
    row4.put(Token.Type.FACT, Action.reduce(6));
    row4.put(Token.Type.CLOSE_BRACKET, Action.reduce(6));
    row4.put(Token.Type.EOL, Action.reduce(6));
    table.put(4, row4);

    Map<Token.Type, Action> row5 = new HashMap<>();
    row5.put(Token.Type.NUM, Action.shift(8));
    row5.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(5, row5);

    Map<Token.Type, Action> row6 = new HashMap<>();
    row6.put(Token.Type.MINUS, Action.reduce(8));
    row6.put(Token.Type.PLUS, Action.reduce(8));
    row6.put(Token.Type.MULT, Action.reduce(8));
    row6.put(Token.Type.FACT, Action.reduce(8));
    row6.put(Token.Type.CLOSE_BRACKET, Action.reduce(8));
    row6.put(Token.Type.EOL, Action.reduce(8));
    table.put(6, row6);

    Map<Token.Type, Action> row7 = new HashMap<>();
    row7.put(Token.Type.MINUS, Action.reduce(10));
    row7.put(Token.Type.PLUS, Action.reduce(10));
    row7.put(Token.Type.MULT, Action.reduce(10));
    row7.put(Token.Type.FACT, Action.shift(14));
    row7.put(Token.Type.CLOSE_BRACKET, Action.reduce(10));
    row7.put(Token.Type.EOL, Action.reduce(10));
    table.put(7, row7);

    Map<Token.Type, Action> row8 = new HashMap<>();
    row8.put(Token.Type.MINUS, Action.reduce(11));
    row8.put(Token.Type.PLUS, Action.reduce(11));
    row8.put(Token.Type.MULT, Action.reduce(11));
    row8.put(Token.Type.FACT, Action.reduce(11));
    row8.put(Token.Type.CLOSE_BRACKET, Action.reduce(11));
    row8.put(Token.Type.EOL, Action.reduce(11));
    table.put(8, row8);

    Map<Token.Type, Action> row9 = new HashMap<>();
    row9.put(Token.Type.NUM, Action.shift(8));
    row9.put(Token.Type.COS, Action.shift(5));
    row9.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(9, row9);

    Map<Token.Type, Action> row10 = new HashMap<>();
    row10.put(Token.Type.NUM, Action.shift(8));
    row10.put(Token.Type.COS, Action.shift(5));
    row10.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(10, row10);

    Map<Token.Type, Action> row11 = new HashMap<>();
    row11.put(Token.Type.NUM, Action.shift(8));
    row11.put(Token.Type.COS, Action.shift(5));
    row11.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(11, row11);

    Map<Token.Type, Action> row12 = new HashMap<>();
    row12.put(Token.Type.NUM, Action.shift(8));
    row12.put(Token.Type.COS, Action.shift(5));
    row12.put(Token.Type.OPEN_BRACKET, Action.shift(9));
    table.put(12, row12);

    Map<Token.Type, Action> row13 = new HashMap<>();
    row13.put(Token.Type.MINUS, Action.reduce(7));
    row13.put(Token.Type.PLUS, Action.reduce(7));
    row13.put(Token.Type.MULT, Action.reduce(7));
    row13.put(Token.Type.FACT, Action.reduce(7));
    row13.put(Token.Type.CLOSE_BRACKET, Action.reduce(7));
    row13.put(Token.Type.EOL, Action.reduce(7));
    table.put(13, row13);

    Map<Token.Type, Action> row14 = new HashMap<>();
    row14.put(Token.Type.MINUS, Action.reduce(9));
    row14.put(Token.Type.PLUS, Action.reduce(9));
    row14.put(Token.Type.MULT, Action.reduce(9));
    row14.put(Token.Type.FACT, Action.reduce(9));
    row14.put(Token.Type.CLOSE_BRACKET, Action.reduce(9));
    row14.put(Token.Type.EOL, Action.reduce(9));
    table.put(14, row14);

    Map<Token.Type, Action> row15 = new HashMap<>();
    row15.put(Token.Type.PLUS, Action.shift(10));
    row15.put(Token.Type.CLOSE_BRACKET, Action.shift(19));
    table.put(15, row15);

    Map<Token.Type, Action> row16 = new HashMap<>();
    row16.put(Token.Type.MINUS, Action.shift(11));
    row16.put(Token.Type.PLUS, Action.reduce(1));
    row16.put(Token.Type.MULT, Action.reduce(1));
    row16.put(Token.Type.FACT, Action.reduce(1));
    row16.put(Token.Type.CLOSE_BRACKET, Action.reduce(1));
    row16.put(Token.Type.EOL, Action.reduce(1));
    table.put(16, row16);

    Map<Token.Type, Action> row17 = new HashMap<>();
    row17.put(Token.Type.MINUS, Action.reduce(3));
    row17.put(Token.Type.PLUS, Action.reduce(3));
    row17.put(Token.Type.MULT, Action.reduce(3));
    row17.put(Token.Type.FACT, Action.reduce(3));
    row17.put(Token.Type.CLOSE_BRACKET, Action.reduce(3));
    row17.put(Token.Type.EOL, Action.reduce(3));
    table.put(17, row17);

    Map<Token.Type, Action> row18 = new HashMap<>();
    row18.put(Token.Type.MINUS, Action.reduce(5));
    row18.put(Token.Type.PLUS, Action.reduce(5));
    row18.put(Token.Type.MULT, Action.reduce(5));
    row18.put(Token.Type.FACT, Action.reduce(5));
    row18.put(Token.Type.CLOSE_BRACKET, Action.reduce(5));
    row18.put(Token.Type.EOL, Action.reduce(5));
    table.put(18, row18);

    Map<Token.Type, Action> row19 = new HashMap<>();
    row19.put(Token.Type.MINUS, Action.reduce(12));
    row19.put(Token.Type.PLUS, Action.reduce(12));
    row19.put(Token.Type.MULT, Action.reduce(12));
    row19.put(Token.Type.FACT, Action.reduce(12));
    row19.put(Token.Type.CLOSE_BRACKET, Action.reduce(12));
    row19.put(Token.Type.EOL, Action.reduce(12));
    table.put(19, row19);

    return table;
  }


  public static Map<Integer, Map<Nonterminal, Integer>> gotoTable = initialiseGotoTable();

  private static Map<Integer, Map<Nonterminal, Integer>> initialiseGotoTable() {
    Map<Integer, Map<Nonterminal, Integer>> table = new HashMap<>();

    Map<Nonterminal, Integer> row0 = new HashMap<>();
    row0.put(Nonterminal.Expr, 1);
    row0.put(Nonterminal.C, 2);
    row0.put(Nonterminal.D, 3);
    row0.put(Nonterminal.E, 4);
    row0.put(Nonterminal.F, 6);
    row0.put(Nonterminal.G, 7);
    table.put(0, row0);

    Map<Nonterminal, Integer> row5 = new HashMap<>();
    row5.put(Nonterminal.F, 13);
    row5.put(Nonterminal.G, 7);
    table.put(5, row5);

    Map<Nonterminal, Integer> row9 = new HashMap<>();
    row9.put(Nonterminal.C, 2);
    row9.put(Nonterminal.D, 3);
    row9.put(Nonterminal.E, 4);
    row9.put(Nonterminal.F, 6);
    row9.put(Nonterminal.G, 7);
    table.put(9, row9);

    Map<Nonterminal, Integer> row10 = new HashMap<>();
    row10.put(Nonterminal.C, 16);
    row10.put(Nonterminal.D, 3);
    row10.put(Nonterminal.E, 4);
    row10.put(Nonterminal.F, 6);
    row10.put(Nonterminal.G, 7);
    table.put(10, row10);

    Map<Nonterminal, Integer> row11 = new HashMap<>();
    row11.put(Nonterminal.D, 17);
    row11.put(Nonterminal.E, 4);
    row11.put(Nonterminal.F, 6);
    row11.put(Nonterminal.G, 7);
    table.put(11, row11);

    Map<Nonterminal, Integer> row12 = new HashMap<>();
    row12.put(Nonterminal.D, 18);
    row12.put(Nonterminal.E, 4);
    row12.put(Nonterminal.F, 6);
    row12.put(Nonterminal.G, 7);
    table.put(12, row12);

    return table;
  }
}
