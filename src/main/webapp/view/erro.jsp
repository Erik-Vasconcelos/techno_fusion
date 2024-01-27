<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isErrorPage="true"%>

<!DOCTYPE html>
<html lang="pt-br">
<%
String contexto = request.getContextPath() + "/view";
%>

<head>
<title>Erro</title>
<meta charset="utf-8">
<link rel="stylesheet" href="<%=contexto%>/erro-style.css">
<link rel="icon" type="image/png"
	href="<%=contexto%>/admin/assets/img/logo-tf.png">
</head>
<link
	href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400"
	rel="stylesheet">
<body>
	<h1 class="titulo t1">${statusCode}</h1>
	<h2 class="titulo t2">${mensagemErro}</h2>
	<div class="gears">
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
		<c:choose>
			<c:when test="${not empty usuario}">
				<a href="<%=request.getContextPath()%>/">
					<button type="button" class="btn btn-dark me-2">Inicio do
						site</button>
				</a>

				<a href="<%=request.getContextPath()%>/inicio">
					<button type="button" class="btn btn-dark me-2">Inicio
						admin</button>
				</a>
			</c:when>
			<c:otherwise>
				<a href="<%=request.getContextPath()%>/">
					<button type="button" class="btn btn-dark me-2">Inicio do
						site</button>
				</a>

			</c:otherwise>
		</c:choose>
	</div>

	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</body>

</html>