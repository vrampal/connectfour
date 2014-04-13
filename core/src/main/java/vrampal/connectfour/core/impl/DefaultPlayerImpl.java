package vrampal.connectfour.core.impl;

import java.awt.Color;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import vrampal.connectfour.core.Player;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(of = { "name" })
class DefaultPlayerImpl implements Player, Serializable {

  @Getter
  private final String name;

  @Getter
  private final char letter;

  @Getter
  private final Color color;

  /**
   * For unit testing only.
   */
  DefaultPlayerImpl() {
    this("", ' ', Color.WHITE);
  }

}
