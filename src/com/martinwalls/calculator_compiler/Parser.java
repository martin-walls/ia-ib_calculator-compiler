package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
      if (A.dotPosition < A.production.body.length) {
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
      }
      i++;
    }
    return new HashSet<>(closure);
  }

  static Set<Item> closure(Item item, Production[] productions) {
    Set<Item> items = new HashSet<>();
    items.add(item);
    return closure(items, productions);
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

  static Item gotoItem(Item i, Symbol X, Production[] productions) {
    Set<Item> allItems = allItems(productions);
    Item gotoTarget = new Item(i.production, i.dotPosition + 1);
    if (allItems.contains(gotoTarget)) {
      return gotoTarget;
    }
    return null;
  }

//  static Set<State> groupItemsIntoStates(Set<Item> items, Nonterminal startSymbol, Production[] productions) {
//    Set<Item> kernelItems = new HashSet<>();
//    for (Item i : items) {
//      if (i.dotPosition > 0 || i.production.head == startSymbol) {
//        kernelItems.add(i);
//      }
//    }
//    Set<State> groups = new HashSet<>();
//    for (Item k : kernelItems) {
//      Set<Item> kSet = new HashSet<>();
//      kSet.add(k);
//      groups.add(new s);
//    }
//    return groups;
//  }

  static Set<Item> getKernelItems(Set<Item> items, Nonterminal startSymbol) {
    Set<Item> kernelItems = new HashSet<>();
    for (Item i : items) {
      if (i.dotPosition > 0 || i.production.head == startSymbol) {
        kernelItems.add(i);
      }
    }
    return kernelItems;
  }

  static Set<Item> getKernelItems(Set<Item> items) {
    Set<Item> kernelItems = new HashSet<>();
    for (Item i : items) {
      if (i.dotPosition > 0) {
        kernelItems.add(i);
      }
    }
    return kernelItems;
  }


  static Map<Integer, Map<Token, Action>> generateActionTable(Map<Integer, Item> states, Production[] productions) {

    Map<Integer, Map<Token, Action>> actions = new HashMap<>();

    Map<Item, Integer> reverseLookupStates = new HashMap<>();
    for (int i : states.keySet()) {
      reverseLookupStates.put(states.get(i), i);
    }

    for (int i : states.keySet()) {
      Map<Token, Action> thisRow = new HashMap<>();

      Item kernelItem = states.get(i);

      Set<Item> closure = closure(kernelItem, productions);


      for (Item c : closure) {
        if (c.dotPosition < c.production.body.length) {
          Symbol symbolAfterDot = c.production.body[c.dotPosition];
          if (symbolAfterDot.isTerminal()) {

            Item gotoItem = gotoItem(c, symbolAfterDot, productions);

            if (gotoItem != null) {
              thisRow.put(symbolAfterDot.terminal, Action.shift(reverseLookupStates.get(gotoItem)))
            }
          }
        } else if (c.dotPosition == c.production.body.length) {

        }
      }

      actions.put(i, thisRow);
    }
  }

  static Map<Integer, Map<Nonterminal, Integer>> generateStatesGotoTable() {

  }


  public static void main(String[] args) {

//    Item f = new Item(Grammar.productions[12], 1);
//
//    Set<Item> i = new HashSet<>();
//    i.add(f);
//
//    Set<Item> c = closure(i, Grammar.productions);
//
//    System.out.println(c);

//    Set<Item> g = calculateGoto(i, new Symbol(new Token(TokenType.PLUS)), Grammar.productions);
//
//    System.out.println(g);
//    System.out.println(closure(g, Grammar.productions));

//    System.out.println(allItems(Grammar.productions));

    // collection of sets for augmented grammar (states)
    Set<Set<Item>> groups = groupItemsIntoStates(allItems(Grammar.productions), Nonterminal.Expr_aug, Grammar.productions);

    for (Set<Item> s : groups) {
      System.out.println(s);
      System.out.println();
    }

  }


}
