<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String contexto = request.getContextPath() + "/view/admin/";
%>
<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="fragmentos/head.jsp"></jsp:include>

<body class="g-sidenav-show bg-gray-100">
	<div
		class="main-content position-relative bg-gray-100 max-height-vh-100 h-100">
		<nav
			class="navbar bg-slate-900 navbar-expand-lg flex-wrap top-0 px-0 py-0">

			<div class="container py-2">
				<nav aria-label="breadcrumb">
					<div class="d-flex align-items-center">
						<span class="px-3 font-weight-bold text-lg text-white me-4">Techno
							Fusion</span>
					</div>
				</nav>
				<ul class="navbar-nav d-none d-lg-flex">
					<li
						class="nav-item px-3 py-3 border-radius-sm  d-flex align-items-center">
						<a href="<%=request.getContextPath()%>/inicio"
						class="nav-link text-white p-0"> Inicio</a>
					</li>
				</ul>
				<div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4"
					id="navbar">
					<ul class="navbar-nav ms-md-auto  justify-content-end">
						<li class="nav-item d-xl-none ps-3 d-flex align-items-center">
							<a href="javascript:;" class="nav-link text-white p-0"
							id="iconNavbarSidenav">
								<div class="sidenav-toggler-inner">
									<i class="sidenav-toggler-line bg-white"></i> <i
										class="sidenav-toggler-line bg-white"></i> <i
										class="sidenav-toggler-line bg-white"></i>
								</div>
						</a>
						</li>
						<li class="nav-item px-3 d-flex align-items-center"><a
							href="javascript:;" class="nav-link text-white p-0"> <svg
									width="16" height="16" xmlns="http://www.w3.org/2000/svg"
									class="fixed-plugin-button-nav cursor-pointer"
									viewBox="0 0 24 24" fill="currentColor">
                  <path fill-rule="evenodd"
										d="M11.078 2.25c-.917 0-1.699.663-1.85 1.567L9.05 4.889c-.02.12-.115.26-.297.348a7.493 7.493 0 00-.986.57c-.166.115-.334.126-.45.083L6.3 5.508a1.875 1.875 0 00-2.282.819l-.922 1.597a1.875 1.875 0 00.432 2.385l.84.692c.095.078.17.229.154.43a7.598 7.598 0 000 1.139c.015.2-.059.352-.153.43l-.841.692a1.875 1.875 0 00-.432 2.385l.922 1.597a1.875 1.875 0 002.282.818l1.019-.382c.115-.043.283-.031.45.082.312.214.641.405.985.57.182.088.277.228.297.35l.178 1.071c.151.904.933 1.567 1.85 1.567h1.844c.916 0 1.699-.663 1.85-1.567l.178-1.072c.02-.12.114-.26.297-.349.344-.165.673-.356.985-.57.167-.114.335-.125.45-.082l1.02.382a1.875 1.875 0 002.28-.819l.923-1.597a1.875 1.875 0 00-.432-2.385l-.84-.692c-.095-.078-.17-.229-.154-.43a7.614 7.614 0 000-1.139c-.016-.2.059-.352.153-.43l.84-.692c.708-.582.891-1.59.433-2.385l-.922-1.597a1.875 1.875 0 00-2.282-.818l-1.02.382c-.114.043-.282.031-.449-.083a7.49 7.49 0 00-.985-.57c-.183-.087-.277-.227-.297-.348l-.179-1.072a1.875 1.875 0 00-1.85-1.567h-1.843zM12 15.75a3.75 3.75 0 100-7.5 3.75 3.75 0 000 7.5z"
										clip-rule="evenodd"></path>
                </svg>
						</a></li>

						<li class="nav-item dropdown pe-2 d-flex align-items-center px-1">
							<a href="javascript:;" class="nav-link text-body p-0"
							id="dropdownMenuButton" data-bs-toggle="dropdown"
							aria-expanded="false"> <c:choose>
									<c:when test="${not empty usuario.imagem}">
										<img src="${usuario.imagem}" class="avatar avatar-sm"
											alt="avatar" />
									</c:when>

									<c:otherwise>
										<img src="<%=contexto%>assets/img/usuario.png"
											class="avatar avatar-sm" alt="avatar" />

									</c:otherwise>
								</c:choose>
						</a>
							<ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4"
								aria-labelledby="dropdownMenuButton">
								<li class="mb-2"><a class="dropdown-item border-radius-md"
									href="<%=request.getContextPath()%>/funcionario/perfil">
										<div class="d-flex py-1">
											<div class="my-auto">
												<img src="<%=contexto%>assets/img/usuario.png"
													class="avatar avatar-sm border-radius-sm  me-3 ">
											</div>
											<div class="d-flex flex-column justify-content-center">
												<h6 class="text-sm font-weight-normal mb-1">
													<span class="font-weight-bold">Perfil</span>
												</h6>
											</div>
										</div>
								</a></li>
								<li class="mb-2"><a class="dropdown-item border-radius-md"
									href="<%=request.getContextPath()%>/logout">
										<div class="d-flex py-1">
											<div class="my-auto">
												<img src="<%=contexto%>assets/img/sair.png"
													class="avatar avatar-sm border-radius-sm p-1 me-3">
											</div>
											<div class="d-flex flex-column justify-content-center">
												<h6 class="text-sm font-weight-normal mb-1">
													<span class="font-weight-bold">Sair</span>
												</h6>
											</div>
										</div>
								</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<hr class="horizontal w-100 my-0 dark">

		</nav>
		
		<c:if test="${not empty resposta.mensagem}">
				<c:if test="${resposta.status eq 'SUCCESS'}">
					<div class="alert alert-success mb-0" id="box-msg">${resposta.mensagem}</div>
				</c:if>

				<c:if test="${resposta.status eq 'INFORMATION'}">
					<div class="alert alert-warning mb-0" id="box-msg">${resposta.mensagem}</div>
				</c:if>

				<c:if test="${resposta.status eq 'ERROR'}">
					<div class="alert alert-danger mb-0" id="box-msg">${resposta.mensagem}</div>
				</c:if>
			</c:if>

		
		<!-- End Sidenav Top -->
		<div class="pt-7 pb-6 bg-cover"
			style="background-image: url('<%=contexto%>assets/img/header-orange-purple.jpg'); background-position: bottom;"></div>
		<div class="container">
			<div class="card card-body py-2 bg-transparent shadow-none">
				<div class="row">
					<div class="col-auto">
						<c:choose>
							<c:when test="${not empty usuario.imagem}">
								<div
									class="avatar avatar-2xl rounded-circle position-relative mt-n7 border border-gray-100 border-4">
									<img src="${usuario.imagem}" alt="profile_image" class="w-100">
								</div>
							</c:when>

							<c:otherwise>
								<div
									class="avatar avatar-2xl rounded-circle position-relative mt-n7 border border-gray-100 border-4">
									<img src="<%=contexto%>assets/img/usuario.png" alt="profile_image"
										class="w-100">
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-auto my-auto">
					<div class="h-100">
						<h3 class="mb-0 font-weight-bold">${usuario.nome}</h3>
						<p class="mb-0">${usuario.email}</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container my-3 py-3">
		<div class="row">
			<div class="col-12 col-xl-12 mb-4">
				<div class="card border shadow-xs h-100">
					<div class="card-header pb-0 p-3">
						<div class="row">
							<div class="col-md-8 col-9">
								<h6 class="mb-0 font-weight-semibold text-lg">Dados do
									perfil</h6>
								<p class="text-sm mb-1">Seus dados cadastrados no sistema</p>
							</div>
						</div>
					</div>
					<div class="card-body p-3">
						<ul class="list-group">
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pt-0 pb-1 text-sm"><span
								class="text-secondary">Nome:</span> &nbsp;${usuario.nome}</li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm"><span
								class="text-secondary">Sexo:</span> &nbsp; ${usuario.sexo}</li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm"><span
								class="text-secondary">Data de nascimento:</span> &nbsp;
								<span id="dataNascimento">${usuario.dataNascimento}</span></li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm"><span
								class="text-secondary">Email:</span> &nbsp; ${usuario.email}</li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm"><span
								class="text-secondary">Salário:</span> &nbsp; <span id="salario">${usuario.salario}</span></li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm"><span
								class="text-secondary">Perfil:</span> &nbsp; ${usuario.perfil}</li>
							<li
								class="list-group-item border-0 ps-0 text-dark font-weight-semibold pb-1 text-sm" ><span
								class="text-secondary">Telfones: </span> &nbsp; 
								
								<c:choose>
									<c:when test="${not empty telefones}">
									<div id="groupTelefones">
										<c:forEach items="${telefones}" var="t">
											<span class="text-dark text-sm">${t.numero}</span><br>
										</c:forEach>
									</div>
									</c:when>

									<c:otherwise>
										<span class="text-dark text-sm">---</span>
									</c:otherwise>
								</c:choose></li>

						</ul>
					</div>
				</div>
			</div>

			<div class="col-12">
				<div class="card shadow-xs border mb-4 pb-2">
					<div class="card-header pb-0 p-3">
						<div class="row">
							<div class="col-md-8 col-9">
								<h6 class="mb-0 font-weight-semibold text-lg">Credenciais</h6>
								<p class="text-sm mb-1">Edite suas informações de login</p>
							</div>
							<div class="col-md-4 col-3 text-end">
								<button type="button" class="btn btn-white btn-icon px-2 py-2"
									onclick="habilitarCampos()">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14"
										viewBox="0 0 24 24" fill="currentColor">
                      <path
											d="M21.731 2.269a2.625 2.625 0 00-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 000-3.712zM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 00-1.32 2.214l-.8 2.685a.75.75 0 00.933.933l2.685-.8a5.25 5.25 0 002.214-1.32L19.513 8.2z"></path>
                    </svg>
								</button>
							</div>
						</div>
					</div>
					<div class="card-body p-3">
						<div class="row">
							<form method="POST"
								action="<%=request.getContextPath()%>/funcionario/perfil"
								id="formCredenciais">
								<input type="hidden" value="${resposta.status}" id="statusResposta">

								<div class="form-group">
									<label for="login" class="form-control-label">Login</label> <input
										class="form-control" type="text" required="required"
										value="${usuario.login}" id="login" name="login" minlength="5"
										maxlength="8" readonly="readonly">
								</div>

								<div class="form-group" id="groupSenha">
									<label for="senha" class="form-control-label">Senha</label> <input class="form-control" type="password"
										readonly="readonly" value="********"
										required="required" id="senha" name="senha">
								</div>

								<div class="form-group" id="groupConfirmSenha"
									style="display: none;">
									<label for="senha" class="form-control-label">Confirme
										a senha</label> <input class="form-control" type="password"
										required="required" id="confirmSenha">
								</div>

								<div class="card-body px-2 py-2 pt-3" id="groupButton"
									style="display: none;">
									<div class="ms-auto d-flex align-items-center"
										id="btn-acoes-form">
										<button type="button" id="bntSalvar" class="btn btn-dark me-2"
											onclick="enviarForm()">Salvar</button>
										<button type="button" id="bntCancelar"
											class="btn btn-danger me-2" onclick="desabilitarCampos()">Cancelar</button>
									</div>
								</div>

							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<footer class="footer pt-3  ">
			<div class="container-fluid">
				<div class="row align-items-center justify-content-lg-between">
					<div class="col-lg-6 mb-lg-0 mb-4">
						<div
							class="copyright text-center text-xs text-muted text-lg-start">
							Copyright ©
							<script>
								document.write(new Date().getFullYear())
							</script>
							Techno Fusion
						</div>
					</div>
				</div>
			</div>
		</footer>
	</div>
	
	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>

	<script src="<%=contexto%>assets/js/script-perfil.js"></script>
</body>

</html>