package vrampal.demo.connectFour.core.impl;

import java.awt.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.demo.connectFour.core.Player;

@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name" })
public class PlayerImpl implements Player {

  public static final PlayerImpl EMPTY = new PlayerImpl("Empty", 'O', Color.WHITE);

  @Getter
  @Setter
  private String name = "";

  @Getter
  @Setter
  private char letter = ' ';

  @Getter
  @Setter
  private Color color = Color.BLACK;

}
