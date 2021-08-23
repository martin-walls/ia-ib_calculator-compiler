package com.martinwalls.calculator_compiler;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Input: source program character stream
 * Output: token stream
 */
public class Lexer {

  class InvalidTokenException extends Exception {
    private final String token;

    public InvalidTokenException(String token) {
      this.token = token;
    }

    public String getToken() {
      return token;
    }
  }

  // most recently read char from stdin
  private char currentChar = ' ';

  /**
   * Lex the input from stdin into a stream of tokens.
   * @return the token stream corresponding to the input, or <code>null</code> if the
   *         input is invalid
   */
  public Queue<Token> lexStdin() throws IOException {
    ArrayDeque<Token> tokenStream = new ArrayDeque<>();
    Token token;
    while (true) {
      // scan next token and make sure it's valid
      try {
        token = scan();
      } catch (InvalidTokenException e) {
        System.out.println("[Lexing error] - invalid token \"" + e.getToken() + "\"");
        // clear any remaining input that hasn't been read yet
        while (System.in.available() > 0 && System.in.read() != '\n') {}
        return null;
      }
      tokenStream.add(token);
      // keep scanning to end of line
      if (token.getType() == Token.Type.EOL) {
        break;
      }
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
      return new UFloat(value);
    }

    // plus
    if (currentChar == '+') {
      currentChar = ' ';
      return new Token(Token.Type.PLUS);
    }

    // minus
    if (currentChar == '-') {
      currentChar = ' ';
      return new Token(Token.Type.MINUS);
    }

    // multiply
    if (currentChar == '*') {
      currentChar = ' ';
      return new Token(Token.Type.MULT);
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
      return new Token(Token.Type.COS);
    }

    // factorial
    if (currentChar == '!') {
      currentChar = ' ';
      return new Token(Token.Type.FACT);
    }

    // brackets
    if (currentChar == '(') {
      currentChar = ' ';
      return new Token(Token.Type.OPEN_BRACKET);
    }
    if (currentChar == ')') {
      currentChar = ' ';
      return new Token(Token.Type.CLOSE_BRACKET);
    }

    // end of line
    if (currentChar == '\n') {
      currentChar = ' ';
      return new Token(Token.Type.EOL);
    }

    // not one of the allowed tokens
    throw new InvalidTokenException(currentChar + "");
  }

}
