<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<jsp:useBean id="helper" class="vrampal.connectfour.servlet.ConnectFourHelper" scope="page" />
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
<h3><%=helper.getSubMessage()%></h3>
<p>Game id: <%=game.getId()%></p>
<table class="cfb"><tbody>
<%=helper.printBoard()%>
</tbody></table>
<p><a href="?reset=1">Play another game</a></p>
</body>
</html>
