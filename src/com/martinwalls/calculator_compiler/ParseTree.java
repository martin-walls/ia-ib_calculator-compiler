package com.martinwalls.calculator_compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ParseTree {

  final Symbol root;
  final List<ParseTree> children;

  public ParseTree(Symbol root, List<ParseTree> children) {
    this.root = root;
    this.children = children;
  }

  public Symbol getRoot() {
    return root;
  }

  public List<ParseTree> getChildren() {
    return children;
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
    StringBuilder sb = new StringBuilder();
    printTreeIntoBuffer(sb, "", "");
    return sb.toString();
  }

  /**
   * Build up string representation of the parse tree
   * @param buffer a string buffer to build the tree representation into
   * @param rootPrefix print this before (to the left of) the root of the tree; so we can
   *                   display nesting correctly
   * @param childrenPrefix print this before (to the left of) any subsequent children; so
   *                       we can display nesting correctly
   */
  private void printTreeIntoBuffer(StringBuilder buffer, String rootPrefix, String childrenPrefix) {
    buffer.append(rootPrefix);
    buffer.append(root);
    int rootNameLen = root.toString().length();

    if (children.size() == 1) {
      // if only one child, print it on same line
      children.get(0).printTreeIntoBuffer(buffer,
          " ─ ",
          childrenPrefix + "   "  + " ".repeat(rootNameLen));
    } else {
      // otherwise, with more or zero children, new line
      buffer.append("\n");

      for (Iterator<ParseTree> it = children.iterator(); it.hasNext(); ) {
        ParseTree nextChild = it.next();
        if (it.hasNext()) {
          nextChild.printTreeIntoBuffer(buffer,
              childrenPrefix + "├" + "─".repeat(rootNameLen) + " ",
              childrenPrefix + "| " + " ".repeat(rootNameLen));
        } else {
          // last child
          nextChild.printTreeIntoBuffer(buffer,
              childrenPrefix + "└" + "─".repeat(rootNameLen) + " ",
              childrenPrefix + "  " + " ".repeat(rootNameLen));
        }
      }
    }
  }



}
