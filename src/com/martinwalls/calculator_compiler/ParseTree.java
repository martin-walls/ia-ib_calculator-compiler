package com.martinwalls.calculator_compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseTree {

  Symbol root;
  List<ParseTree> children;

  public ParseTree(Symbol root, List<ParseTree> children) {
    this.root = root;
    this.children = children;
  }

  public ParseTree(Symbol root) {
    this.root = root;
    children = new ArrayList<>();
  }

  public ParseTree addChildren(ParseTree... newChildren) {
    children.addAll(Arrays.asList(newChildren));
    return this;
  }

  @Override
  public String toString() {
    return root + (children.isEmpty() ? "" : " " + children);
  }
}
