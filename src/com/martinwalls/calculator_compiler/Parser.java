package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;
import com.martinwalls.calculator_compiler.tokens.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {

  /**
   * Calculate the closure of the given item set in the given set of productions.
   */
  static Set<Item> closure(Set<Item> items, Production[] productions) {

    List<Item> closure = new ArrayList<>(items);

    int i=0;

    while (i < closure.size()) {
      Item A = closure.get(i);
      Symbol symbolToRightOfDot = A.production.body[A.dotPosition];
      if (symbolToRightOfDot.type == Symbol.Type.Nonterminal) {
        Nonterminal B = symbolToRightOfDot.nonterminal;
        for (Production BProduction : productions) {
          if (BProduction.head == B) {
            Item BItem = new Item(BProduction, 0);
            if (!closure.contains(BItem)) {
              closure.add(BItem);
            }
          }
        }
      }
      i++;
    }
    return new HashSet<>(closure);
  }

  static Set<Item> allItems(Production[] productions) {
    Set<Item> items = new HashSet<>();
    for (Production p : productions) {
      int length = p.body.length;
      for (int dotPos = 0; dotPos <= length; dotPos++) {
        items.add(new Item(p, dotPos));
      }
    }
    return items;
  }

  static Set<Item> calculateGoto(Set<Item> I, Symbol X, Production[] productions) {
    Set<Item> allItems = allItems(productions);
    Set<Item> gotoItems = new HashSet<>();
    for (Item j : allItems) {
      if (j.dotPosition > 0 &&j.production.body[j.dotPosition - 1].equals(X)) {
        for (Item i : I) {
          if (j.production.equals(i.production) && j.dotPosition == i.dotPosition + 1) {
            gotoItems.add(j);
          }
        }
      }
    }
    return gotoItems;
  }

  public static void main(String[] args) {

    Item f = new Item(Grammar.productions[1], 1);

    Set<Item> i = new HashSet<>();
    i.add(f);
//
//    Set<Item> c = closure(i, Grammar.productions);
//
//    System.out.println(c);

    Set<Item> g = calculateGoto(i, new Symbol(new Token(TokenType.PLUS)), Grammar.productions);

    System.out.println(g);
    System.out.println(closure(g, Grammar.productions));
  }

}
