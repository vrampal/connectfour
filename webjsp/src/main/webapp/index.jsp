<%@ page import="vrampal.connectfour.core.*" %>
<%@ page import="vrampal.connectfour.core.impl.GameImpl" %>
<html>
<body>
<%
	Game game = (Game) session.getAttribute("game");
	if (game == null) {
	  game = new GameImpl();
	  session.setAttribute("game", game);
	}

	String colStr = request.getParameter("col");
	if (colStr != null) {
	  int colIdx = Integer.parseInt(colStr);
	  game.dropDisc(colIdx - 1);
	}

	if (game.getStatus() == GameStatus.FINISHED) {
		session.setAttribute("game", null);
		Player winner = game.getWinner();
		if (winner != null) {
%><h2><%= winner.getName() %> won the game.</h2><%
		} else {
%><h2>It's a draw game.</h2><%
		}
%><p><a href="index.jsp">Play another game</a><%
	}
	else if (game.getStatus() == GameStatus.ONGOING) {
		Player player = game.getCurrentPlayer();
%><h2>Now playing: <%= player.getName() %></h2><%
	}
%>
<table border="">
<%
	Board board = game.getBoard();
	int height = board.getHeight();
	int width = board.getWidth();

	if (game.getStatus() == GameStatus.ONGOING) {
%><tr>
<%
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        %><td><a href="index.jsp?col=<%= colIdx %>">V</a></td>
<%
		}
	}
%></tr>
<%
    for (int rowIdx = height; rowIdx > 0; rowIdx--) {
%><tr>
<%
      for (int colIdx = 1; colIdx <= width; colIdx++) {
        char c = board.getCell(colIdx - 1, rowIdx - 1).getLetter();
        String s;
        if (c == ' ') {
          s = "&nbsp;";
        } else {
          s = Character.toString(c);
        }
        %><td><%= s %></td>
<%
      }
%></tr>
<%
    }
%>
</table>
</body>
</html>
