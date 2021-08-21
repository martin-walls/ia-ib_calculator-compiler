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

  /**
   * Calculate the closure of the given item set in the given set of productions.
   */
  Set<Item> closure(Set<Item> items, Production[] productions) {

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

  Set<Item> closure(Item item, Production[] productions) {
    Set<Item> items = new HashSet<>();
    items.add(item);
    return closure(items, productions);
  }

  Set<Item> allItems(Production[] productions) {
    Set<Item> items = new HashSet<>();
    for (Production p : productions) {
      int length = p.body.length;
      for (int dotPos = 0; dotPos <= length; dotPos++) {
        items.add(new Item(p, dotPos));
      }
    }
    return items;
  }


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
//        System.out.println("    [Parsing error]");
        break;
      }

      if (action.getType() == Action.Type.Shift) {
        // shift to next input symbol
        Token thisToken = input.remove();
        // new state
        int newState = action.getValue();
        stack.push(newState);
        parseTreeStack.push(new ParseTree(new Symbol(thisToken)));

//        System.out.println("    [Shift " + newState + "]");

      } else if (action.getType() == Action.Type.Reduce) {
        // which production to reduce by
        int productionNo = action.getValue();
        Production production = Grammar.productions[productionNo];

        ParseTree reducedTree = new ParseTree(new Symbol(production.head));
        ParseTree[] treeChildren = new ParseTree[production.getLength()];

        // pop body of production from the stack
        for (int i = 0; i < production.getLength(); i++) {
          stack.pop();
          treeChildren[production.getLength() - 1 - i] = parseTreeStack.pop();
        }

        reducedTree.addChildren(treeChildren);
        parseTreeStack.push(reducedTree);

        // find new state from goto table
        int newState = gotoTable.get(stack.peek()).get(production.head);
        stack.push(newState);

//        System.out.println("    [Reduce " + productionNo + "] " + production.toString());

      } else if (action.getType() == Action.Type.Accept) {
//        System.out.println("    [Accept]\n");
        return(parseTreeStack.pop());
      }

    }
    // we shouldn't ever get here; if we have, something went wrong parsing
    return null;
  }





}
