package vrampal.connectfour.core.impl;

import static org.mockito.Mockito.mock;
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

  // Object under test
  private BoardImpl board;

  private Player playerA = mock(Player.class);

  private Player playerB = mock(Player.class);

  private GameEndListener endGame = mock(GameEndListener.class);

  @Given("a board $width by $height")
  public void createGame(int width, int height) {
    board = new BoardImpl(width, height);
    board.setEndGameListener(endGame);
  }

  @When("playerA drop in column $colIdx")
  public void dropA(int colIdx) {
    board.dropDisc(playerA, colIdx);
  }

  @When("playerB drop in column $colIdx")
  public void dropB(int colIdx) {
    board.dropDisc(playerB, colIdx);
  }

  @Then("playerA is a winner")
  public void checkWinnerA() {
    verify(endGame).victory(playerA);
  }

  @Then("playerB is a winner")
  public void checkWinnerB() {
    verify(endGame).victory(playerB);
  }

  @Then("there is no winner")
  public void checkNoWinner() {
    verifyZeroInteractions(endGame);
  }

}
