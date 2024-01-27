<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String contexto = request.getContextPath() + "/view/usuarios/";

String valorPesquisa = (String) request.getAttribute("valorPesquisa");

String urlLink = "";

if (valorPesquisa != null && !valorPesquisa.trim().isEmpty()) {
	urlLink = request.getContextPath() + "/pesquisar?valor=" + valorPesquisa + "&page=";

} else {
	urlLink = request.getContextPath() + "/pesquisar?page=";
}
%>

<!DOCTYPE html>
<html lang="pt-br">
<jsp:include page="fragmentos/head.jsp"></jsp:include>

<body>
	<jsp:include page="fragmentos/header.jsp"></jsp:include>

	<nav id="navigation">
		<div class="container">
			<div id="responsive-nav">
				<!-- NAV -->
				<ul class="main-nav nav navbar-nav">
					<li class=""><a href="<%=request.getContextPath()%>">Home</a></li>
					<li class="active"><a href="#">Pesquisa</a></li>
				</ul>
				<!-- /NAV -->
			</div>
			<!-- /responsive-nav -->
		</div>
		<!-- /container -->
	</nav>
	<!-- /NAVIGATION -->
	<!-- SECTION -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!-- STORE -->
				<div id="store" class="col-md-9">
					<!-- store products -->
					<div class="row">
						<!-- product -->

						<c:choose>
							<c:when test="${not empty pagination.content}">
								<c:forEach items="${pagination.content}" var="p">
									<c:set var="valorOriginal"
										value="${p.valor - (p.valor * (p.desconto / 100))}" />
									<%
									double valor = (Double) pageContext.getAttribute("valorOriginal");
									DecimalFormat df = new DecimalFormat("#,##0.00");
									String valorFormatado = df.format(valor);
									%>

									<a href="<%=request.getContextPath()%>/viewProduto?id=${p.id}">
										<div class="col-md-4 col-xs-6">
											<div class="product">
												<div class="product-img">
													<c:choose>
														<c:when test="${not empty p.imagem}">
															<img src="${p.imagem}">
														</c:when>
														<c:otherwise>
															<img src="<%=contexto%>img/produto.jpg">
														</c:otherwise>
													</c:choose>
													<div class="product-label">
														<c:if test="${p.desconto > 0}">
															<span class="sale">${p.desconto} % OFF</span>
														</c:if>
													</div>
												</div>

												<div class="product-body">
													<p class="product-category">${p.marca.nome}</p>
													<h3 class="product-name">${p.descricao}</h3>

													<h4 class="product-price">
														R$
														<%=valorFormatado%><del class="product-old-price">
															R$ ${p.valor}</del>
													</h4>
												</div>
												<div class="add-to-cart">
													<button class="add-to-cart-btn">Ver produto</button>
												</div>
											</div>
										</div>
									</a>
								</c:forEach>
							</c:when>

							<c:otherwise>
								<h3 class="product-name">Nenhum produto encontrado!</h3>
							</c:otherwise>

						</c:choose>
						<!-- /product -->

					</div>
					<!-- /store products -->

					<!-- store bottom filter -->
					<div class="store-filter clearfix">
						<span class="store-qty">Página
							${pagination.pageable.pageNumber} de ${pagination.totalPages}</span>
						<ul class="store-pagination">

							<c:choose>
								<c:when test="${pagination.pageable.pageNumber > 1}">
									<li><a
										href="<%=urlLink%>${pagination.pageable.pageNumber - 1}">
											<i class="fa fa-angle-left"></i>
									</a></li>
								</c:when>
								<c:otherwise>
									<li><span class="disable"> <i
											class="fa fa-angle-left"></i></span></li>
								</c:otherwise>
							</c:choose>

							<c:forEach var="i" begin="1" end="${pagination.totalPages}"
								step="1">
								<c:choose>
									<c:when test="${i eq pagination.pageable.pageNumber}">
										<li class="active">${i}</li>
									</c:when>
									<c:otherwise>
										<li><a href="<%=urlLink%>${i}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:choose>
								<c:when
									test="${pagination.pageable.pageNumber < pagination.totalPages}">
									<li><a
										href="<%=urlLink%>${pagination.pageable.pageNumber + 1}">
											<i class="fa fa-angle-right"></i>
									</a></li>
								</c:when>
								<c:otherwise>
									<li><span class="disable"> <i
											class="fa fa-angle-right"></i></span></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<!-- /store bottom filter -->
				</div>
				<!-- /STORE -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /SECTION -->


	<jsp:include page="fragmentos/footer.jsp"></jsp:include>

	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>

</body>
</html>
