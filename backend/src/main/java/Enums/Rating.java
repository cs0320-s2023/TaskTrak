package Enums;

public enum Rating {
    HIGHEST(5),
    HIGH(4),
    MEDIUM(3),
    LOW(2),
    LOWEST(1);

    private int value;

    // Constructor
    private Rating(int value) {
      this.value = value;
    }

    // Getter
    public int getValue() {
      return value;
    }
  }



