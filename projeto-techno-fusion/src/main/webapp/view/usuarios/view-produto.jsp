<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
					<li class=""><a href="<%=request.getContextPath()%>">Home</a></li>
					<li class="active"><a href="#">Produto</a></li>
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

			<c:choose>
				<c:when test="${not empty produto}">
					<div class="row">
						<!-- Product main img -->
						<div class="col-md-5 col-md-push-2">
							<div id="product-main-img">
								<div class="product-preview">
									<c:choose>
										<c:when test="${not empty produto.imagem}">
											<img src="${produto.imagem}">
										</c:when>
										<c:otherwise>
											<img src="<%=contexto%>img/produto.jpg">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<!-- /Product main img -->

						<!-- Product thumb imgs -->
						<div class="col-md-2  col-md-pull-5">
							<div id="product-imgs">
								<div class="product-preview">
									<c:choose>
										<c:when test="${not empty produto.imagem}">
											<img src="${produto.imagem}">
										</c:when>
										<c:otherwise>
											<img src="<%=contexto%>img/produto.jpg">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<!-- /Product thumb imgs -->


						<!-- Product details -->
						<div class="col-md-5">
							<div class="product-details">
								<h2 class="product-name">${produto.descricao}</h2>
								<div>
									<h3 class="product-price">
										R$ ${produto.valor - (produto.valor * (produto.desconto / 100))}
										<del class="product-old-price">${produto.valor}</del>
									</h3>
									<span class="product-available">${produto.marca.nome}</span>
								</div>
								<p>${produto.descricao}</p>

								<div class="add-to-cart">
									<button class="add-to-cart-btn">
										<i class="fa fa-shopping-cart"></i> |
									</button>
								</div>

							</div>
						</div>
						<!-- /Product details -->

					</div>
					<!-- /row -->

				</c:when>

				<c:otherwise>
					<h2 class="product-name">Nenhum produto encontrado!</h2>
				</c:otherwise>

			</c:choose>
		</div>
		<!-- /container -->
	</div>
	<!-- /SECTION -->

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
										<div class="product">
											<a
												href="<%=request.getContextPath()%>/viewProduto?id=${p.id}">
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
													</div>
												</div>
											</a>
											<div class="product-body">
												<p class="product-category">${p.marca.nome}</p>
												<h3 class="product-name">
													<a href="#">${p.descricao}</a>
												</h3>
												<h4 class="product-price">
													R$ ${p.valor - (p.valor * (p.desconto / 100))}
													<del class="product-old-price"> R$ ${p.valor}</del>
												</h4>
											</div>
											<div class="add-to-cart">
												<a
													href="<%=request.getContextPath()%>/viewProduto?id=${p.id}">
													<button class="add-to-cart-btn">Ver produto</button>
												</a>
											</div>
										</div>
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

</body>
</html>
