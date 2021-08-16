package com.martinwalls.calculator_compiler;

import com.martinwalls.calculator_compiler.tokens.Number;
import com.martinwalls.calculator_compiler.tokens.Token;
import com.martinwalls.calculator_compiler.tokens.TokenType;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Input: source program character stream
 * Output: token stream
 */
public class Lexer {

  class InvalidTokenException extends RuntimeException {
    private final String token;

    public InvalidTokenException(String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }
  }

  class InvalidInputException extends RuntimeException {
    private final String invalidToken;

    public InvalidInputException(InvalidTokenException e) {
      this.invalidToken = e.getToken();
    }

    public String getInvalidToken() {
      return invalidToken;
    }
  }

  // most recently read char from stdin
  private char currentChar = ' ';

  /**
   * Lex the input from stdin into a stream of tokens.
   * @return the token stream corresponding to the input
   * @throws InvalidInputException if the input contains illegal tokens
   */
  public Queue<Token> lexStdin() throws IOException, InvalidInputException {
    ArrayDeque<Token> tokenStream = new ArrayDeque<>();
    Token token;
    while (true) {
      // scan next token and make sure it's valid
      try {
        token = scan();
      } catch (InvalidTokenException e) {
//        System.out.println("Invalid token: " + e.getToken());
        throw new InvalidInputException(e);
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
   * Advance <code>currentChar</code> to next char from stdin
   */
  private void nextChar() throws IOException {
    currentChar = (char) System.in.read();
  }

  /**
   * Read from stdin to scan for the next token.
   * @return the next token in the input
   * @throws InvalidTokenException if the input doesn't match any allowed token
   */
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
      return new Token(TokenType.PLUS);
    }

    // minus
    if (currentChar == '-') {
      currentChar = ' ';
      return new Token(TokenType.MINUS);
    }

    // multiply
    if (currentChar == '*') {
      currentChar = ' ';
      return new Token(TokenType.MULT);
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
      return new Token(TokenType.COS);
    }

    // factorial
    if (currentChar == '!') {
      currentChar = ' ';
      return new Token(TokenType.FACT);
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
