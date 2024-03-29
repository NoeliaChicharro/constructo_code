package ch.constructo.frontend.ui.util;

public enum Position {

  ABSOLUTE("absolute"), FIXED("fixed"), RELATIVE("relative");

  private final String value;

  Position(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
