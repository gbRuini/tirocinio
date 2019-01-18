<!DOCTYPE html>
<html>
<head>
<title>Piattaforma MultiChain</title>
</head>
<body>


	<h1>BlockChain salvo e Gabri</h1>
<br>
<br>

<form method="post" action="upload" enctype="multipart/form-data">
	File:
	<input type="file" name="file"/> <br/>
	<br>
	<input type="submit" value="Upload" name="upload" id="upload" />
</form>

<br>
<br>

 <form action='webApp' method="post"> 
 	<input type="text" name="key" placeholder="inserisci testo" value= '<%=request.getAttribute("fileName")%>' /> 
 	<input type="submit" value="Publish" name="publishbtn" />
 	<input type="submit" value="Cerca" name="cercabtn" />
 
 </form> 
 
 <p>
 <%if(request.getAttribute("log") != null) {%>
 <%=request.getAttribute("log") %>
 <% } %>
</p>










</body>
</html>
