<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String contexto = request.getContextPath() + "/view/usuarios/";
%>

<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Techno Fusion</title>

		<link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700"
			rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="<%=contexto%>css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="<%=contexto%>css/slick.css" />
		<link type="text/css" rel="stylesheet" href="<%=contexto%>css/slick-theme.css" />
		<link type="text/css" rel="stylesheet" href="<%=contexto%>css/nouislider.min.css" />
		<link rel="stylesheet" href="<%=contexto%>css/font-awesome.min.css">
		<link type="text/css" rel="stylesheet" href="<%=contexto%>css/style.css" />
</head>