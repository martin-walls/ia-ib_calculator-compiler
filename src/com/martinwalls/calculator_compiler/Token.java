package com.martinwalls.calculator_compiler;

public class Token {
  public enum Type {
    UFLOAT,
    PLUS,
    MINUS,
    MULT,
    COS,
    FACT,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    EOL;
  }

  private final Type type;
  // optional value field, to allow flexibility if subclasses need it
  protected Object value;

  public Token(Type t) {
    this.type = t;
  }

  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return type.name();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Token)) return false;
    Token t = (Token) o;
    // check if same token type
    if (this.type != t.type) return false;
    // check equality of value
    return valueEquals(t);
  }

  /**
   * Checks for equality between this.value and t.value.
   *
   * Subclasses implementing a defined single type should override this instead of equals().
   */
  protected boolean valueEquals(Token t) {
    // by default, Token has no value, so just return true
    return true;
  }

  @Override
  public int hashCode() {
    return type.hashCode() ^ valueHashcode();
  }

  /**
   * Returns a hashcode for the value field of this Token.
   *
   * Subclasses implementing a defined single type should override this instead of hashCode().
   */
  protected int valueHashcode() {
    return 0;
  }
}
