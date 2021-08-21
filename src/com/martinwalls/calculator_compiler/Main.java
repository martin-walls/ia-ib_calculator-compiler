package com.martinwalls.calculator_compiler;

import java.io.IOException;
import java.util.Queue;

public class Main {

  public static void main(String[] args) throws IOException {
    while (true) {
      // prompt
      System.out.print("\n> ");

      Lexer lexer = new Lexer();
      Queue<Token> input = lexer.lexStdin();
      // catch lexing error
      if (input == null) {
        System.out.println("\n[Could not lex input. Resetting]");
        continue;
      }

      Parser parser = new Parser();
      ParseTree parseTree = parser.parse(input, Grammar.actionTable, Grammar.gotoTable);
      // catch parsing error
      if (parseTree == null) {
        System.out.println("\n[Could not parse input. Resetting]");
        continue;
      }
      System.out.println("[Parse tree]");
      System.out.println(parseTree);

      Evaluator evaluator = new Evaluator();
      double result = evaluator.evaluate(parseTree);
      System.out.println("[Result] " + result);
    }
  }

}
