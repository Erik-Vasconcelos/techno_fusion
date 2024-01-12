<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page isErrorPage="true" %>

<html lang="pt-br">
<%
String contexto = request.getContextPath() + "/view";
%>

<head>
<title>Erro</title>
<link rel="stylesheet" href="<%=contexto%>/erro-style.css">
<link rel="icon" type="image/png" href="<%=contexto%>/admin/assets/img/favicon.png">
</head>
<link
	href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400"
	rel="stylesheet">
<body>
	<h1 class="titulo t1">${statusCode}</h1>
	<h2 class="titulo t2">
		${mensagemErro}
	</h2>
	<div class="gears" >
		<div class="gear one">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
		<div class="gear two">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
		<div class="gear three">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
	</div>

	<div class="t2">
		<a href="<%=request.getContextPath()%>/inicio">
			<button type="button" class="btn btn-dark me-2">Inicio</button>
		</a>
	</div>

	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</body>


</html>