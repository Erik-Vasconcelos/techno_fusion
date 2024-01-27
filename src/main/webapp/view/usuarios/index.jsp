<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String contexto = request.getContextPath() + "/view/usuarios/";
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
					<li class="active"><a href="#">Home</a></li>
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
				<!-- shop -->
				<div class="col-md-4 col-xs-6">
					<div class="shop">
						<div class="shop-img">
							<img src="<%=contexto%>img/shop01.png" alt>
						</div>
						<div class="shop-body">
							<h3>
								Coleções<br>de Notebooks
							</h3>
						</div>
					</div>
				</div>
				<!-- /shop -->

				<!-- shop -->
				<div class="col-md-4 col-xs-6">
					<div class="shop">
						<div class="shop-img">
							<img src="<%=contexto%>img/shop03.png" alt>
						</div>
						<div class="shop-body">
							<h3>
								Coleções<br>de Acessórios
							</h3>
						</div>
					</div>
				</div>
				<!-- /shop -->

				<!-- shop -->
				<div class="col-md-4 col-xs-6">
					<div class="shop">
						<div class="shop-img">
							<img src="<%=contexto%>img/shop02.png" alt>
						</div>
						<div class="shop-body">
							<h3>
								Coleções<br>de Câmeras
							</h3>
						</div>
					</div>
				</div>
				<!-- /shop -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /SECTION -->

	<!-- SECTION -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">

				<!-- section title -->
				<div class="col-md-12">
					<div class="section-title">
						<h3 class="title">Novos produtos</h3>
					</div>
				</div>
				<!-- /section title -->

				<!-- Products tab & slick -->
				<div class="col-md-12">
					<div class="row">
						<div class="products-tabs">
							<!-- tab -->
							<div id="tab1" class="tab-pane active">
								<div class="products-slick" data-nav="#slick-nav-1">

									<c:forEach items="${produtosRecentes}" var="p">
										<c:set var="valorOriginal"
											value="${p.valor - (p.valor * (p.desconto / 100))}" />
										<%
										double valor = (Double) pageContext.getAttribute("valorOriginal");
										DecimalFormat df = new DecimalFormat("#,##0.00");
										String valorFormatado = df.format(valor);
										%>

										<a href="<%=request.getContextPath()%>/viewProduto?id=${p.id}">
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
															<span class="sale">${p.desconto} %</span>
														</c:if>

														<span class="new">NEW</span>
													</div>
												</div>

												<div class="product-body">
													<p class="product-category">${p.marca.nome}</p>
													<h3 class="product-name">${p.descricao}</h3>
													<h4 class="product-price">
														R$
														<%=valorFormatado%>
														<del class="product-old-price"> R$ ${p.valor}</del>
													</h4>
												</div>
												<div class="add-to-cart">
													<button class="add-to-cart-btn">Ver produto</button>

												</div>
											</div>
										</a>
									</c:forEach>

								</div>
								<div id="slick-nav-1" class="products-slick-nav"></div>
							</div>
							<!-- /tab -->
						</div>
					</div>
				</div>
				<!-- Products tab & slick -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /SECTION -->

	<!-- HOT DEAL SECTION -->
	<div id="hot-deal" class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-12">
					<div class="hot-deal">
						<ul class="hot-deal-countdown">
							<li>
								<div>
									<h3 id="days">0</h3>
									<span>Dias</span>
								</div>
							</li>
							<li>
								<div>
									<h3 id="hours">0</h3>
									<span>Horas</span>
								</div>
							</li>
							<li>
								<div>
									<h3 id="minutes">0</h3>
									<span>Minutos</span>
								</div>
							</li>
							<li>
								<div>
									<h3 id="seconds">0</h3>
									<span>Segundos</span>
								</div>
							</li>
						</ul>

						<h2 class="text-uppercase">Ofertas quentes esta semana</h2>
						<p>Nova coleção com até 50% OFF</p>
					</div>
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /HOT DEAL SECTION -->

	<!-- SECTION -->
	<div class="section mb-5">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">

				<!-- section title -->
				<div class="col-md-12">
					<div class="section-title">
						<h3 class="title">Top ofertas</h3>
					</div>
				</div>
				<!-- /section title -->

				<!-- Products tab & slick -->
				<div class="col-md-12">
					<div class="row">
						<div class="products-tabs">
							<!-- tab -->
							<div id="tab2" class="tab-pane fade in active">
								<div class="products-slick" data-nav="#slick-nav-2">
									<c:forEach items="${produtosTopOfertas}" var="p">
										<c:set var="valorOriginal"
											value="${p.valor - (p.valor * (p.desconto / 100))}" />
										<%
										double valor = (Double) pageContext.getAttribute("valorOriginal");
										DecimalFormat df = new DecimalFormat("#,##0.00");
										String valorFormatado = df.format(valor);
										%>

										<a href="<%=request.getContextPath()%>/viewProduto?id=${p.id}">
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
															<span class="sale">${p.desconto} %</span>
														</c:if>

														<span class="new">NEW</span>
													</div>
												</div>

												<div class="product-body">
													<p class="product-category">${p.marca.nome}</p>
													<h3 class="product-name">${p.descricao}</h3>
													<h4 class="product-price">R$ <%=valorFormatado%>
														<del class="product-old-price"> R$ ${p.valor}</del>
													</h4>
												</div>
												<div class="add-to-cart">
													<button class="add-to-cart-btn">Ver produto</button>

												</div>
											</div>
										</a>
									</c:forEach>
								</div>
								<div id="slick-nav-2" class="products-slick-nav"></div>
							</div>
							<!-- /tab -->
						</div>
					</div>
				</div>
				<!-- /Products tab & slick -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>

	<jsp:include page="fragmentos/footer.jsp"></jsp:include>

	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>
	<script src="<%=contexto%>js/index.js"></script>

</body>
</html>
