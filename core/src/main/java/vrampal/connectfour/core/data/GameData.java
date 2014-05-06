package vrampal.connectfour.core.data;

import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.connectfour.core.Board;
import vrampal.connectfour.core.Game;
import vrampal.connectfour.core.GameStatus;
import vrampal.connectfour.core.Player;

/**
 * Pure data class for serialization.
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString(of = { "id" })
public class GameData implements Serializable {

  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private int nbPlayers;

  @Getter
  @Setter
  private PlayerData[] players;

  @Getter
  @Setter
  private int width;

  @Getter
  @Setter
  private int height;

  @Getter
  @Setter
  private char[] board;

  @Getter
  @Setter
  private GameStatus status;

  @Getter
  @Setter
  private int turnNumber;

  @Getter
  @Setter
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
    board = new char[width * height];
    for (int colIdx = 0; colIdx < width; colIdx++) {
      for (int rowIdx = 0; rowIdx < height; rowIdx++) {
        Player curCell = inBoard.getCell(colIdx, rowIdx);
        board[(colIdx * height) + rowIdx] = curCell.getLetter();
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
