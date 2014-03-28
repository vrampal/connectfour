package vrampal.demo.connectFour.core;

import java.awt.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
class Player {

  public static final Player EMPTY = new Player("Empty", 'O', Color.WHITE);

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private char letter;

  @Getter
  @Setter
  private Color color;

}
