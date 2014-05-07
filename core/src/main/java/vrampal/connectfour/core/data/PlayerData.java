package vrampal.connectfour.core.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vrampal.connectfour.core.Player;

/**
 * Pure data class for serialization.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(of = { "name" })
public class PlayerData implements Player, Serializable {

  private static final long serialVersionUID = -6674494555843465952L;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private char letter;

}
