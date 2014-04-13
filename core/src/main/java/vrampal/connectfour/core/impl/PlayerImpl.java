package vrampal.connectfour.core.impl;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.connectfour.core.Player;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(of = { "name" })
class PlayerImpl implements Player, Serializable {

  private static final long serialVersionUID = 9163473157263454692L;

  @Getter
  @Setter
  private String name = "";

  @Getter
  @Setter
  private char letter = ' ';

}
