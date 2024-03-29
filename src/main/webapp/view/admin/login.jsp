<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String contexto = request.getContextPath() + "/view/admin/";
%>

<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="fragmentos/head.jsp"></jsp:include>

<body class="">
	<div class="container position-sticky z-index-sticky top-0">

		<div class="row">
			<div class="col-12">
				<!-- Navbar -->
				<nav
					class="navbar navbar-expand-lg blur border-radius-sm top-0 z-index-3 shadow position-absolute my-3 py-2 start-0 end-0 mx-4">
					<div class="container-fluid px-1">
						<a class="navbar-brand font-weight-bolder ms-lg-0 "
							href="dashboard.html">Techno Fusion</a>
						<button class="navbar-toggler shadow-none ms-2" type="button"
							data-bs-toggle="collapse" data-bs-target="#navigation"
							aria-controls="navigation" aria-expanded="false"
							aria-label="Toggle navigation">
							<span class="navbar-toggler-icon mt-2"> <span
								class="navbar-toggler-bar bar1"></span> <span
								class="navbar-toggler-bar bar2"></span> <span
								class="navbar-toggler-bar bar3"></span>
							</span>
						</button>
					</div>
				</nav>
				<!-- End Navbar -->
			</div>
		</div>
	</div>
	<main class="main-content  mt-0">

		<section>
			<div class="page-header min-vh-100">
				<div class="container">
					<div class="row">
						<div class="col-xl-4 col-md-6 d-flex flex-column mx-auto">

							<c:if test="${not empty resposta.mensagem}">
								<c:if test="${resposta.status eq 'SUCCESS'}">
									<div class="alert alert-success mt-5" id="box-msg">${resposta.mensagem}</div>
								</c:if>

								<c:if test="${resposta.status eq 'INFORMATION'}">
									<div class="alert alert-warning mt-5" id="box-msg">${resposta.mensagem}</div>
								</c:if>

								<c:if test="${resposta.status eq 'ERROR'}">
									<div class="alert alert-danger mt-5" id="box-msg">${resposta.mensagem}</div>
								</c:if>
							</c:if>

							<div class="card card-plain mt-2">
								<div class="card-header pb-0 text-left bg-transparent">
									<h3 class="font-weight-black text-dark display-6">Bem
										vindo</h3>
									<p class="mb-0">Por favos, insira seus dados!</p>
								</div>
								<div class="card-body">
									<form action="<%=request.getContextPath()%>/login"
										method="POST" id="loginForm">

								<!-- ############ REMOVER MODIFICACAO LOGIN ESTATICO ######################## -->
										<label>Login</label>
										<div class="mb-3">
											<!--<input type="text" class="form-control" required="required"
												placeholder="Digite seu login" aria-label="login"
												aria-describedby="login-campo" name="login" id="login">-->
												
											<input type="text" class="form-control"
												placeholder="Digite seu login" aria-label="login"
												aria-describedby="login-campo" name="login" id="login">
										</div>
										<label>Senha</label>
										<div class="mb-3">
											<!--<input type="password" class="form-control" required="required"
												placeholder="Digite sua senha" aria-label="Senha"
												aria-describedby="password-addon" name="senha" id="senha">-->
											<input type="password" class="form-control" 
												placeholder="Digite sua senha" aria-label="Senha"
												aria-describedby="password-addon" name="senha" id="senha">
										</div>
										
										<div class="text-center">
											<input type="submit" value="Entrar"
												class="btn btn-dark w-100 mt-4 mb-3">

											<!--										<button type="button" class="btn btn-dark w-100 mt-4 mb-3">Entrar</button>-->
										</div>
									</form>
								</div>

							</div>
						</div>
						<div class="col-md-6">
							<div
								class="position-absolute w-40 top-0 end-0 h-100 d-md-block d-none">
								<div
									class="oblique-image position-absolute fixed-top ms-auto h-100 z-index-0 bg-cover ms-n8"
									style="background-image: url('<%=contexto%>/assets/img/image-sign-in.jpg')">
									<div
										class="blur mt-12 p-4 text-center border border-white border-radius-md position-absolute fixed-bottom m-4">
										<h2 class="mt-3 text-dark font-weight-bold">Entre para o
											nosso time Techno Fusion</h2>
										<h6 class="text-dark text-sm mt-5">
											Copyright ©
											<script>
												document.write(new Date()
														.getFullYear())
											</script>
											Corporate Techno Fusion
										</h6>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>
</body>

</html>