<jsp:directive.page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true"
		import="static vrampal.connectfour.webjsp.SessionKeys.*"
		import="static vrampal.connectfour.webjsp.RequestAttributeKeys.*" />
<jsp:useBean id="boardPrinter" class="vrampal.connectfour.webjsp.view.BoardPrinter" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>ConnectFour</title>
<link rel="stylesheet" href="connectfour.css">
</head>
<body>
	<h1><%=request.getAttribute(ATTR_MAIN_MESSAGE_KEY)%></h1>
	<h2><%=request.getAttribute(ATTR_SUB_MESSAGE_KEY)%></h2>
	<p>Game id: <%=request.getAttribute(ATTR_GAME_ID_KEY)%></p>
	<table class="cfb">
		<tbody>
			<% boardPrinter.printBoard(session, out); %>
		</tbody>
	</table>
	<p><a href="?reset=1">Play another game</a></p>
</body>
</html>
