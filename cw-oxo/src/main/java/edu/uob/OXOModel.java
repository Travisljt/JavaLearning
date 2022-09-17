package edu.uob;

import java.util.ArrayList;


class OXOModel {

    ArrayList<ArrayList<OXOPlayer>> cell;
    ArrayList<OXOPlayer> columnOfBoard;
    ArrayList<OXOPlayer> players;
    private int currentPlayerNumber;
    private OXOPlayer winner;
    private boolean gameDrawn;
    private int winThreshold;

    public OXOModel(int numberOfRows, int numberOfColumns, int winThresh) {
        winThreshold = winThresh;

        cell = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            columnOfBoard = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                columnOfBoard.add(new OXOPlayer(' '));
            }
            cell.add(columnOfBoard);
        }

        players = new ArrayList<>();
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public void addPlayer(OXOPlayer player) {
        players.add(player);
    }

    public OXOPlayer getPlayerByNumber(int number) {
        return players.get(number);
    }

    public OXOPlayer getWinner() {
        return winner;
    }

    public void setWinner(OXOPlayer player) {
        winner = player;
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int playerNumber) {
        currentPlayerNumber = playerNumber;
    }

    public int getNumberOfRows() {
        return cell.size();
    }

    public int getNumberOfColumns() {
        return columnOfBoard.size();
    }

    public OXOPlayer getCellOwner(int rowNumber, int colNumber) {
        return cell.get(rowNumber).get(colNumber);
    }

    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player) {

        cell.get(rowNumber).set(colNumber,player);

    }

    public void setWinThreshold(int winThresh) {
        winThreshold = winThresh;
    }

    public int getWinThreshold() {
        return winThreshold;
    }

    public void setGameDrawn() {
        gameDrawn = true;
    }

    public boolean isGameDrawn() {
        return gameDrawn;
    }

}
