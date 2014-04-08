<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:useBean id="helper"
	class="vrampal.connectfour.servlet.ConnectFourHelper" scope="page" />
<jsp:directive.page import="vrampal.connectfour.core.*" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ConnectFourJSP</title>
<link rel="stylesheet" href="test.css" type="text/css" media="screen">
</head>
<body>
	<%
	  helper.pageBegin(request);
	  Game game = helper.getGame();
	%>
	<p>Game id: <%=game.getId()%></p>
	<h2><%=helper.getMainMessage()%></h2>
	<%
	  String subMessage = helper.getSubMessage();
	  if (subMessage != null) {
	    out.print("<p>");
	    out.print(subMessage);
	    out.print("</p>");
	  }

	  if (game.getStatus() == GameStatus.FINISHED) {
	    out.print("<p><a href=\"index.jsp\">Play another game</a></p>");
	  }
	%>
	<table class="board">
		<tbody>
			<%
			  Board board = game.getBoard();
			  int height = board.getHeight();
			  int width = board.getWidth();

			  if (game.getStatus() == GameStatus.ONGOING) {
			    out.print("<tr>");
			    for (int colIdx = 1; colIdx <= width; colIdx++) {
			      out.print("<td align=\"center\"><a href=\"index.jsp?col=");
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
			      String clazz = "empty";
			      if (c == 'R') {
			        clazz = "red";
			      } else if (c == 'Y') {
			        clazz = "yellow";
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
	<%
	  helper.pageEnd(request);
	%>
</body>
</html>
