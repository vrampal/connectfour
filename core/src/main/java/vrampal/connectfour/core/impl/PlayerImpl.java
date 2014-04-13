package vrampal.connectfour.core.impl;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.connectfour.core.Player;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString(of = { "name" })
class PlayerImpl implements Player, Serializable {

  private static final long serialVersionUID = 9163473157263454692L;

  @Getter
  @Setter(AccessLevel.PACKAGE)
  private String name = "";

  @Getter
  @Setter(AccessLevel.PACKAGE)
  private char letter = ' ';

}
