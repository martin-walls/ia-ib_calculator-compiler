package com.martinwalls.calculator_compiler;

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

  public static void main(String[] args) {

    Item f = new Item(Grammar.productions[0], 0);

    Set<Item> i = new HashSet<>();
    i.add(f);

    Set<Item> c = closure(i, Grammar.productions);

    System.out.println(c);

  }

}
