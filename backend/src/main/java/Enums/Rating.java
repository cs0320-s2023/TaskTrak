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

  public static Rating fromValue(int value) {
    for (Rating rating : Rating.values()) {
      if (rating.getValue() == value) {
        return rating;
      }
    }
    throw new IllegalArgumentException("Invalid rating value: " + value);
  }
  }



