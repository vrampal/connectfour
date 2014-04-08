package vrampal.connectfour.core.impl;

import java.awt.Color;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.connectfour.core.Player;

@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name" })
public class DefaultPlayerImpl implements Player, Serializable {

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
