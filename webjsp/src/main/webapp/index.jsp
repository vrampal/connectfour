<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:useBean id="helper"
	class="vrampal.connectfour.servlet.ConnectFourHelper" scope="page" />
<jsp:directive.page import="vrampal.connectfour.core.*" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ConnectFour</title>
<link rel="stylesheet" href="connectfour.css" type="text/css" media="screen">
</head>
<body>
	<%
	  helper.pageBegin(request);
	  Game game = helper.getGame();
	%>
	<h2><%=helper.getMainMessage()%></h2>
	<h2><%=helper.getSubMessage()%></h2>
	<p>Game id: <%=game.getId()%></p>
	<table class="cfb">
		<tbody>
			<%
			  Board board = game.getBoard();
			  int height = board.getHeight();
			  int width = board.getWidth();

			  if (game.getStatus() == GameStatus.ONGOING) {
			    out.print("<tr>");
			    for (int colIdx = 1; colIdx <= width; colIdx++) {
			      out.print("<td class=\"cfh\"><a href=\"index.jsp?col=");
			      out.print(colIdx);
			      out.print("\">V</a></td>");
			    }
			    out.print("</tr>");
			  }

			  for (int rowIdx = height; rowIdx > 0; rowIdx--) {
			    out.print("<tr>");
			    for (int colIdx = 1; colIdx <= width; colIdx++) {
			      Player content = board.getCell(colIdx - 1, rowIdx - 1);
			      char c = content.getLetter();
			      String clazz = "cfe";
			      if (c == 'R') {
			        clazz = "cfr";
			      } else if (c == 'Y') {
			        clazz = "cfy";
			      }
			      out.print("<td class=\"");
			      out.print(clazz);
			      out.print("\"></td>");
			    }
			    out.print("</tr>");
			  }
			%>
		</tbody>
	</table>
	<p><a href="index.jsp?reset=1">Play another game</a></p>
</body>
</html>
