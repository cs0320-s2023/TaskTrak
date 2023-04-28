package Enums;

public enum Rating {

    HIGH(2),
    MEDIUM(1),
    LOW(0);

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



