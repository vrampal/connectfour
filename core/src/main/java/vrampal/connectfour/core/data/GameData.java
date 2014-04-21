package vrampal.connectfour.core.data;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

/**
 * Pure data class for serialization.
 */
@Data
@NoArgsConstructor
@ToString(of = { "id" })
public class GameData implements Serializable {

  private static final long serialVersionUID = 2649138486471544477L;

  private String id;

  private int nbPlayers;

  private PlayerData[] players;

  private int width;

  private int height;

  private char[][] board;

  private GameStatus status;

  private int turnNumber;

  private String winner;

  public GameData(Game in) {
    id = in.getId();

    List<Player> inPlayers = in.getAllPlayers();
    nbPlayers = inPlayers.size();
    players = new PlayerData[nbPlayers];
    inPlayers.toArray(players);

    Board inBoard = in.getBoard();
    width = inBoard.getWidth();
    height = inBoard.getHeight();
    board = new char[width][];
    for (int colIdx = 0; colIdx < width; colIdx++) {
      board[colIdx] = new char[height];
      for (int rowIdx = 0; rowIdx < height; rowIdx++) {
        Player curCell = inBoard.getCell(colIdx, rowIdx);
        board[colIdx][rowIdx] = curCell.getLetter();
      }
    }

    status = in.getStatus();
    turnNumber = in.getTurnNumber();
    Player inWinner = in.getWinner();
    if (inWinner == null) {
      winner = null;
    } else {
      winner = inWinner.toString();
    }
  }

}
