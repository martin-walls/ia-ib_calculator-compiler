package com.martinwalls.calculator_compiler;

import java.io.IOException;
import java.net.NetPermission;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Input: source program character stream
 * Output: token stream
 */
public class Lexer {

  class InvalidTokenException extends RuntimeException {
    private String token;

    public InvalidTokenException(String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }
  }

  private char currentChar = ' ';
  private ArrayDeque<Token> tokenStream;

  public Queue<Token> lexStdin() throws IOException {
    tokenStream = new ArrayDeque<>();
    Token token;
    while (true) {
      // scan next token and make sure it's valid
      try {
        token = scan();
      } catch (InvalidTokenException e) {
        System.out.println("Invalid token: " + e.getToken());
        return null;
      }
      // keep scanning to end of line
      if (token.getType() == TokenType.EOL) {
        break;
      }
      tokenStream.add(token);
    }
    return tokenStream;
  }

  /**
   * Advance currentChar to next char from Stdin
   */
  private void nextChar() throws IOException {
    currentChar = (char) System.in.read();
  }

  private Token scan() throws IOException, InvalidTokenException {
    // skip whitespace
    while (Character.isWhitespace(currentChar) && currentChar != '\n') {
      nextChar();
    }

    // float
    if (Character.isDigit(currentChar) || currentChar == '.') {
      StringBuilder b = new StringBuilder();
      do {
        b.append(currentChar);
        nextChar();
      } while (Character.isDigit(currentChar) || currentChar == '.');
      double value;
      // parse double from chars; catch number format exception, eg. if more than
      // one decimal point
      try {
        value = Double.parseDouble(b.toString());
      } catch (NumberFormatException e) {
        throw new InvalidTokenException(b.toString());
      }
      return new Number(value);
    }

    // plus
    if (currentChar == '+') {
      currentChar = ' ';
      return new BinaryOp(TokenType.PLUS);
    }

    // minus
    if (currentChar == '-') {
      currentChar = ' ';
      return new BinaryOp(TokenType.MINUS);
    }

    // multiply
    if (currentChar == '*') {
      currentChar = ' ';
      return new BinaryOp(TokenType.MULT);
    }

    // cos
    if (currentChar == 'c') {
      // check next two chars to make sure input is "cos"
      nextChar();
      if (currentChar != 'o') {throw new InvalidTokenException("c" + currentChar);}
      nextChar();
      if (currentChar != 's') {throw new InvalidTokenException("co" + currentChar);}
      // input is "cos"
      currentChar = ' ';
      return new UnaryPrefixOp(TokenType.COS);
    }

    // factorial
    if (currentChar == '!') {
      currentChar = ' ';
      return new UnaryPostfixOp(TokenType.FACT);
    }

    // brackets
    if (currentChar == '(') {
      currentChar = ' ';
      return new Token(TokenType.OPEN_BRACKET);
    }
    if (currentChar == ')') {
      currentChar = ' ';
      return new Token(TokenType.CLOSE_BRACKET);
    }

    // end of line
    if (currentChar == '\n') {
      currentChar = ' ';
      return new Token(TokenType.EOL);
    }

    // not one of the allowed tokens
    throw new InvalidTokenException(currentChar + "");
  }

  public static void main(String[] args) throws IOException {
    Lexer l = new Lexer();
    while (true) {
      Queue<Token> tokens = l.lexStdin();

      while (!tokens.isEmpty()) {
        System.out.print(tokens.poll() + " ");
      }
    }
  }

}
