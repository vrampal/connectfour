<%@page import="vrampal.connectfour.servlet.RequestAttributeKeys"%>
<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:useBean id="boardPrinter" class="vrampal.connectfour.servlet.BoardPrinter" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>ConnectFour</title>
<link rel="stylesheet" href="connectfour.css" type="text/css" media="screen">
</head>
<body>
	<h2><%=request.getAttribute(RequestAttributeKeys.ATTR_MAIN_MESSAGE_KEY)%></h2>
	<h3><%=request.getAttribute(RequestAttributeKeys.ATTR_SUB_MESSAGE_KEY)%></h3>
	<p>Game id: <%=request.getAttribute(RequestAttributeKeys.ATTR_GAME_ID_KEY)%></p>
	<table class="cfb">
		<tbody>
			<%=boardPrinter.printBoard(request.getAttribute(RequestAttributeKeys.ATTR_GAME_KEY))%>
		</tbody>
	</table>
	<p><a href="?reset=1">Play another game</a></p>
</body>
</html>
