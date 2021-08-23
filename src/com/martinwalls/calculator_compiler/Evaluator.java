package com.martinwalls.calculator_compiler;

import java.util.List;

public class Evaluator {

  public double evaluate(ParseTree parseTree) {
    return expr(parseTree.getChildren());
  }

  private double expr(List<ParseTree> children) {
    if (children.size() == 1) {
      // expr -> C
      return C(children.get(0).getChildren());
    } else {
      // expr -> expr + C
      double expr = expr(children.get(0).getChildren());
      double c = C(children.get(2).getChildren());
      return expr + c;
    }
  }

  private double C(List<ParseTree> children) {
    if (children.size() == 1) {
      // C -> D
      return D(children.get(0).getChildren());
    } else {
      // C -> C - D
      double c = C(children.get(0).getChildren());
      double d = D(children.get(2).getChildren());
      return c - d;
    }
  }

  private double D(List<ParseTree> children) {
    if (children.size() == 1) {
      // D -> E
      return E(children.get(0).getChildren());
    } else {
      // D -> E * D
      double e = E(children.get(0).getChildren());
      double d = D(children.get(2).getChildren());
      return e * d;
    }
  }

  private double E(List<ParseTree> children) {
    if (children.size() == 1) {
      // E -> F
      return F(children.get(0).getChildren());
    } else {
      // E -> cos F
      double f = F(children.get(1).getChildren());
      return Math.cos(f);
    }
  }

  private double F(List<ParseTree> children) {
    if (children.size() == 1) {
      // F -> sG
      return sG(children.get(0).getChildren());
    } else {
      // F -> uG!
      double g = uG(children.get(0).getChildren());
      return factorial(g);
    }
  }

  private double sG(List<ParseTree> children) {
    if (children.size() == 1) {
      // sG -> uG
      return uG(children.get(0).getChildren());
    } else {
      // sG -> - ufloat
      double ufloat = ufloat(children.get(1));
      return -ufloat;
    }
  }

  private double uG(List<ParseTree> children) {
    if (children.size() == 1) {
      // uG -> ufloat
      return ufloat(children.get(0));
    } else {
      // uG -> ( expr )
      return expr(children.get(1).getChildren());
    }
  }

  private double ufloat(ParseTree tree) {
    Symbol root = tree.getRoot();
    if (root.isTerminal()) {
      if (root.getTerminal().getType() == Token.Type.UFLOAT) {
        return ((UFloat) root.getTerminal()).getValue();
      }
    }
    // if not a ufloat token
    return Double.NaN;
  }

  private double factorial(double x) {
    // check if x is an integer
    if (x == Math.floor(x)) {
      int xInt = (int) Math.floor(x);
      int result = 1;
      for (; xInt >= 1; xInt--) {
        result *= xInt;
      }
      return result;
    }
    // can't do factorial if not an integer
    return Double.NaN;
  }

}
