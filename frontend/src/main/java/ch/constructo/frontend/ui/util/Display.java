package ch.constructo.frontend.ui.util;

public enum Display {

  BLOCK("block"), INLINE("inline"), FLEX("flex"), INLINE_FLEX("inline-flex");

  private final String value;

  Display(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
