package edu.uob;

public class OXOMoveException extends Exception {

  private static final long serialVersionUID = -2629325698018774532L;

  public OXOMoveException(String message) {
    super(message);
  }

  enum RowOrColumn {
    ROW,
    COLUMN
  }

  public static class OutsideCellRangeException extends OXOMoveException {
    private static final long serialVersionUID = -2405736440969523511L;

    public OutsideCellRangeException(RowOrColumn dimension, int pos) {
      super("Position " + pos + " is out of range for " + dimension.name());
    }
  }

  public static class InvalidIdentifierLengthException extends OXOMoveException {
    private static final long serialVersionUID = 8061335505061402995L;

    public InvalidIdentifierLengthException(int length) {
      super("Identifier of size " + length + " is invalid");
    }
  }

  public static class InvalidIdentifierCharacterException extends OXOMoveException {

    private static final long serialVersionUID = 3292975112861815343L;

    public InvalidIdentifierCharacterException(RowOrColumn problemDimension, char character) {
      super(character + " is not a valid character for a " + problemDimension.name());
    }
  }

  public static class CellDoesNotExistException extends OXOMoveException {
    private static final long serialVersionUID = -3237469957618113527L;

    public CellDoesNotExistException(int row, int column) {
      super("Cell [" + row + "," + column + "] has already been claimed");
    }
  }

  public static class CellAlreadyTakenException extends OXOMoveException {
    private static final long serialVersionUID = 6355491353457021580L;

    public CellAlreadyTakenException(int row, int column) {
      super("Cell [" + row + "," + column + "] has already been claimed");
    }
  }
}
