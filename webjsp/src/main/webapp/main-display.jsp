<jsp:directive.page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true"
	import="vrampal.connectfour.webjsp.ConnectFourRequest" />
<jsp:useBean id="boardPrinter" class="vrampal.connectfour.webjsp.view.BoardPrinter" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>ConnectFour</title>
<link rel="stylesheet" href="connectfour.css" />
</head>
<body>
	<h1><%=request.getAttribute(ConnectFourRequest.MAIN_MESSAGE)%></h1>
	<h2><%=request.getAttribute(ConnectFourRequest.SUB_MESSAGE)%></h2>
	<p>Game id: <%=request.getAttribute(ConnectFourRequest.GAME_ID)%></p>
	<table class="cfb">
		<tbody>
			<%=boardPrinter.printBoard(session) %>
		</tbody>
	</table>
	<p><a href="?reset=1">Play another game</a></p>
</body>
</html>
