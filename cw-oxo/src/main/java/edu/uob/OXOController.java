package edu.uob;

import java.util.ArrayList;

import edu.uob.OXOMoveException.*;

class OXOController {
    OXOModel gameModel;


    public OXOController(OXOModel model) {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException {
        char[] str = command.toCharArray();
        //'A' and 'a' represent the same meaning
        int rowNumber = Character.toLowerCase(str[0]) - 'a';
        int columNumber = str[1] - '1';
        int currentPlayerNumber = gameModel.getCurrentPlayerNumber();
        OXOPlayer player = gameModel.getPlayerByNumber(currentPlayerNumber);
        int boardRowNumber = gameModel.getNumberOfRows();
        int boardColumNumber = gameModel.getNumberOfColumns();

        //Check whether it is a valid grid or not
        if (str.length != 2) {

            throw new OXOMoveException.InvalidIdentifierLengthException(str.length);
        } else if (Character.toLowerCase(str[0]) < 97 || Character.toLowerCase(str[0]) > 105) {//if str[0] is not equal to [a-i]

            throw new OXOMoveException.InvalidIdentifierCharacterException(RowOrColumn.ROW, str[0]);

        } else if (str[1] < 49 || str[1] > 57) {//if str[1] is not equal to [1-9]

            throw new OXOMoveException.InvalidIdentifierCharacterException(RowOrColumn.COLUMN, str[1]);
        } else if (rowNumber >= boardRowNumber) {

            throw new OXOMoveException.OutsideCellRangeException(RowOrColumn.ROW, rowNumber);
        } else if (columNumber >= boardColumNumber) {

            throw new OXOMoveException.OutsideCellRangeException(RowOrColumn.COLUMN, columNumber + 1);
        } else if (gameModel.cell.get(rowNumber).get(columNumber).getPlayingLetter() != ' ') {

            throw new OXOMoveException.CellAlreadyTakenException(rowNumber, columNumber);
        } else {
            gameModel.setCellOwner(rowNumber, columNumber, player);
            int verticalCounter, horizontalCounter, diagonalCounter1 = 0, diagonalCounter2 = 0;
            
            int allStep = gameModel.getNumberOfColumns() * gameModel.getNumberOfRows();

            //Check horizontally and vertically
            for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
                horizontalCounter = 0;
                verticalCounter = 0;
                for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
                    if (gameModel.cell.get(i).get(j).getPlayingLetter() == player.getPlayingLetter()) {
                        horizontalCounter++;
                    }
                    if (horizontalCounter == gameModel.getWinThreshold()) {
                        gameModel.setWinner(player);
                        break;
                    }
                    if (gameModel.cell.get(j).get(i).getPlayingLetter() == player.getPlayingLetter()) {
                        verticalCounter++;
                    }
                    if (verticalCounter == gameModel.getWinThreshold()) {
                        gameModel.setWinner(player);
                        break;
                    }
                }


            }
            //Check diagonally from left to right
            if (gameModel.getWinner() == null) {
                for (int i = 0, j = 0; i < gameModel.getNumberOfRows() && j < gameModel.getNumberOfColumns(); i++, j++) {
                    if (gameModel.cell.get(i).get(j).getPlayingLetter() == player.getPlayingLetter()) {
                        diagonalCounter1++;
                    }
                    if (diagonalCounter1 == gameModel.getWinThreshold()) {
                        gameModel.setWinner(player);
                        break;
                    }
                }
            }
            //Check diagonally from right to left
            if (gameModel.getWinner() == null) {
                for (int i = (gameModel.getNumberOfRows() - 1), j = 0; i >= 0 && j < gameModel.getNumberOfColumns(); i--, j++) {
                    if (gameModel.cell.get(i).get(j).getPlayingLetter() == player.getPlayingLetter()) {
                        diagonalCounter2++;
                    }
                    if (diagonalCounter2 == gameModel.getWinThreshold()) {
                        gameModel.setWinner(player);

                        break;
                    }
                }
            }
            //Check numbers of players, then change the states
            if (gameModel.getWinner() == null) {
                int playerNumbers = gameModel.getNumberOfPlayers();
                int nextPlayerNumber = currentPlayerNumber + 1;
                if (nextPlayerNumber == playerNumbers) {
                    gameModel.setCurrentPlayerNumber(0);
                } else {
                    gameModel.setCurrentPlayerNumber(nextPlayerNumber);
                }
            } else {
                gameModel.setCurrentPlayerNumber(0);
            }

            //Check if the grid all have been fill in
            for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
                for (int j = 0; j < gameModel.getNumberOfColumns(); j++) {
                    if (gameModel.cell.get(i).get(j).getPlayingLetter() != ' ') {
                        allStep--;
                    }
                }
            }
            //Check if it is game drawn or not
            if (allStep == 0 && gameModel.getWinner() == null) {
                gameModel.setGameDrawn();
            }


        }

    }

    public void addRow() {
        //Assume that it only has 9x9, and when getWinner is null, then add row
        if (gameModel.getNumberOfRows() < 9 && gameModel.getWinner() == null) {
            ArrayList<OXOPlayer> newColum = new ArrayList<>();
            for (int i = 0; i < gameModel.columnOfBoard.size(); i++) {
                newColum.add(new OXOPlayer(' '));
            }
            gameModel.cell.add(newColum);
        }
    }

    public void removeRow() {
        //Assume that it only has 9x9, and when getWinner is null, then remove row
        if (gameModel.getNumberOfRows() > 1 && gameModel.getWinner() == null) {
            gameModel.cell.remove(gameModel.cell.size() - 1);
        }
    }

    public void addColumn() {
        //Assume that it only has 9x9, and when getWinner is null, then remove colum
        if (gameModel.getNumberOfColumns() < 9 && gameModel.getWinner() == null) {
            for (int i = 0; i < gameModel.cell.size(); i++) {
                gameModel.cell.get(i).add(new OXOPlayer(' '));
            }
        }

    }

    public void removeColumn() {
        //Assume that it only has 9x9, and when getWinner is null, then remove colum
        if (gameModel.getNumberOfColumns() > 1 && gameModel.getWinner() == null) {
            for (int i = 0; i < gameModel.cell.size(); i++) {
                gameModel.cell.get(i).remove(gameModel.columnOfBoard.size() - 1);
            }
        }
    }

    public void increaseWinThreshold() {
        //Although the winThreshold should be more than 9, but the controller couldn't affect
        int winThreshold = gameModel.getWinThreshold();
        gameModel.setWinThreshold(winThreshold + 1);

    }

    public void decreaseWinThreshold() {
        int winThreshold = gameModel.getWinThreshold();
        //I would like to say, negative number doesn't mean anything
        if (winThreshold > 1) {
            gameModel.setWinThreshold(winThreshold - 1);
        } else {
            gameModel.setWinThreshold(winThreshold);
        }

    }
}
