package vrampal.connectfour.core.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import vrampal.connectfour.core.Player;

/**
 * JBehave mapping between story and java code.
 */
public class BoardStorySteps {

  private final Player playerA = mock(Player.class);

  private final Player playerB = mock(Player.class);

  // Object under test
  private BoardImpl board;

  private GameEndListener endGame;

  // ----- Given -----

  @Given("a board $width by $height")
  public void GivenABoard(int width, int height) {
    board = new BoardImpl(width, height);
    endGame = mock(GameEndListener.class);
    board.setEndGameListener(endGame);
  }

  // ----- When -----

  @When("playerA drop in column $colIdx")
  public void whenPlayerADrop(int colIdx) {
    board.dropDisc(playerA, colIdx);
  }

  @When("playerB drop in column $colIdx")
  public void whenPlayerBDrop(int colIdx) {
    board.dropDisc(playerB, colIdx);
  }

  // ----- Then -----

  @Then("cell $colIdx,$rowIdx is empty")
  public void thenCellEmpty(int colIdx, int rowIdx) {
    Player player = board.getCell(colIdx, rowIdx);
    assertSame(board.getEmptyPlayer(), player);
  }

  @Then("cell $colIdx,$rowIdx is playerA")
  public void thenCellPlayerA(int colIdx, int rowIdx) {
    Player player = board.getCell(colIdx, rowIdx);
    assertSame(playerA, player);
  }

  @Then("cell $colIdx,$rowIdx is playerB")
  public void thenCellPlayerB(int colIdx, int rowIdx) {
    Player player = board.getCell(colIdx, rowIdx);
    assertSame(playerB, player);
  }

  @Then("there is no winner")
  public void thenNoWinner() {
    verifyZeroInteractions(endGame);
  }

  @Then("playerA is a winner")
  public void thenWinnerA() {
    verify(endGame).victory(playerA);
    verify(endGame, never()).victory(playerB);
    verify(endGame, never()).drawGame();
  }

  @Then("playerB is a winner")
  public void thenWinnerB() {
    verify(endGame, never()).victory(playerA);
    verify(endGame).victory(playerB);
    verify(endGame, never()).drawGame();
  }

  @Then("it's a draw game")
  public void thenDrawGame() {
    verify(endGame, never()).victory(playerA);
    verify(endGame, never()).victory(playerB);
    verify(endGame).drawGame();
  }

}
