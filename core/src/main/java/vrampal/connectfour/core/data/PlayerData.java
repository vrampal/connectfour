package vrampal.connectfour.core.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vrampal.connectfour.core.Player;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "name" })
public class PlayerData implements Player, Serializable {

  private static final long serialVersionUID = 9163473157263454692L;

  private String name;

  private char letter;

}
