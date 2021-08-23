package com.martinwalls.calculator_compiler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Parser {

  private final boolean PRINT_PARSE_STEPS = false;

  public ParseTree parse(Queue<Token> input,
                         Map<Integer, Map<Token.Type, Action>> actionTable,
                         Map<Integer, Map<Nonterminal, Integer>> gotoTable) {

    Deque<Integer> stack = new ArrayDeque<>();
    // initial state
    stack.push(0);

    Deque<ParseTree> parseTreeStack = new ArrayDeque<>();

    while (input.peek() != null) {

      int currentState = stack.peek();
      Token currentInput = input.peek();
      Action action = actionTable.get(currentState).get(currentInput.getType());

      if (action == null) {
        // error
        if (PRINT_PARSE_STEPS) {
          System.out.println("    [Parsing error]");
        }
        break;
      }

      if (action.getType() == Action.Type.Shift) {
        // shift to next input symbol
        Token thisToken = input.remove();
        // new state
        int newState = action.getValue();
        stack.push(newState);
        parseTreeStack.push(new ParseTree(new Symbol(thisToken)));

        if (PRINT_PARSE_STEPS) {
          System.out.println("    [Shift " + newState + "]");
        }

      } else if (action.getType() == Action.Type.Reduce) {
        // which production to reduce by
        int productionNo = action.getValue();
        Production production = Grammar.productions[productionNo];

        ParseTree reducedTree = new ParseTree(new Symbol(production.getHead()));
        ParseTree[] treeChildren = new ParseTree[production.getLength()];

        // pop body of production from the stack
        for (int i = 0; i < production.getLength(); i++) {
          stack.pop();
          treeChildren[production.getLength() - 1 - i] = parseTreeStack.pop();
        }

        reducedTree.addChildren(treeChildren);
        parseTreeStack.push(reducedTree);

        // find new state from goto table
        int newState = gotoTable.get(stack.peek()).get(production.getHead());
        stack.push(newState);

        if (PRINT_PARSE_STEPS) {
          System.out.println("    [Reduce " + productionNo + "] " + production.toString());
        }

      } else if (action.getType() == Action.Type.Accept) {
        if (PRINT_PARSE_STEPS) {
          System.out.println("    [Accept]\n");
        }
        return (parseTreeStack.pop());
      }

    }
    // if we get here, something went wrong parsing
    return null;
  }

  /**
   * Calculate the closure of the given item set in the given set of productions.
   */
  private static Set<Item> closure(Set<Item> items, Production[] productions) {

    List<Item> closure = new ArrayList<>(items);

    int i = 0;

    while (i < closure.size()) {
      Item A = closure.get(i);
      if (A.getDotPosition() < A.getProduction().getLength()) {
        Symbol symbolToRightOfDot = A.getProduction().getBody()[A.getDotPosition()];
        if (symbolToRightOfDot.type == Symbol.Type.Nonterminal) {
          Nonterminal B = symbolToRightOfDot.nonterminal;
          for (Production BProduction : productions) {
            if (BProduction.getHead() == B) {
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

  private static Set<Item> allItems(Production[] productions) {
    Set<Item> items = new HashSet<>();
    for (Production p : productions) {
      for (int dotPos = 0; dotPos <= p.getLength(); dotPos++) {
        items.add(new Item(p, dotPos));
      }
    }
    return items;
  }

}
