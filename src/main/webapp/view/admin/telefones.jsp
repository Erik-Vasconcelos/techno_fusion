<%@page import="br.com.jdevtreinamentos.tf.model.Funcionario"%>
<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity"%>
<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.StatusResposta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String contexto = request.getContextPath() + "/view/admin/";

%>
<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="fragmentos/head.jsp"></jsp:include>

<body class="g-sidenav-show  bg-gray-100">
	<jsp:include page="fragmentos/menu.jsp"></jsp:include>

	<main
		class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
		<!-- Navbar -->

		<c:if test="${not empty resposta.mensagem}">
			<c:if test="${resposta.status eq 'SUCCESS'}">
				<div class="alert alert-success" id="box-msg">${resposta.mensagem}</div>
			</c:if>

			<c:if test="${resposta.status eq 'INFORMATION'}">
				<div class="alert alert-warning" id="box-msg">${resposta.mensagem}</div>
			</c:if>

			<c:if test="${resposta.status eq 'ERROR'}">
				<div class="alert alert-danger" id="box-msg">${resposta.mensagem}</div>
			</c:if>
		</c:if>

		<div class="preloader-container" id="preloader">
			<div class="loader"></div>
		</div>

		<nav
			class="navbar navbar-main navbar-expand-lg mx-5 px-0 shadow-none rounded"
			id="navbarBlur" navbar-scroll="true">

			<div class="container-fluid py-1 px-2">
				<nav aria-label="breadcrumb">
					<ol
						class="breadcrumb bg-transparent mb-1 pb-0 pt-1 px-0 me-sm-6 me-5">
						<li class="breadcrumb-item text-sm"><a
							class="opacity-5 text-dark" href="javascript:;">Funcionários</a></li>
						<li class="breadcrumb-item text-sm text-dark active"
							aria-current="page">Atualizar</li>
						<li class="breadcrumb-item text-sm text-dark active"
							aria-current="page">Telefones</li>
					</ol>

					<a
						href="<%=request.getContextPath()%>/funcionario?idFuncionario=${funcionarioTelefone.id}"><button
							type="button" class="btn btn-sm btn-dark me-2 mt-1">&lt
							Voltar</button></a>
				</nav>
				<div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4"
					id="navbar">
					<div class="ms-md-auto pe-md-3 d-flex align-items-center">
						<div class="input-group">
							<span class="input-group-text text-body bg-white  border-end-0 ">
								<svg xmlns="http://www.w3.org/2000/svg" width="16px"
									height="16px" fill="none" viewBox="0 0 24 24"
									stroke-width="1.5" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
                  </svg>
							</span> <input type="text" class="form-control ps-0"
								placeholder="Search">
						</div>
					</div>
					<ul class="navbar-nav  justify-content-end">
						<li class="nav-item d-xl-none ps-3 d-flex align-items-center">
							<a href="javascript:;" class="nav-link text-body p-0"
							id="iconNavbarSidenav">
								<div class="sidenav-toggler-inner">
									<i class="sidenav-toggler-line"></i> <i
										class="sidenav-toggler-line"></i> <i
										class="sidenav-toggler-line"></i>
								</div>
						</a>
						</li>
						<li class="nav-item px-3 d-flex align-items-center"><a
							href="javascript:;" class="nav-link text-body p-0"> <svg
									width="16" height="16" xmlns="http://www.w3.org/2000/svg"
									class="fixed-plugin-button-nav cursor-pointer"
									viewBox="0 0 24 24" fill="currentColor">
                    <path fill-rule="evenodd"
										d="M11.078 2.25c-.917 0-1.699.663-1.85 1.567L9.05 4.889c-.02.12-.115.26-.297.348a7.493 7.493 0 00-.986.57c-.166.115-.334.126-.45.083L6.3 5.508a1.875 1.875 0 00-2.282.819l-.922 1.597a1.875 1.875 0 00.432 2.385l.84.692c.095.078.17.229.154.43a7.598 7.598 0 000 1.139c.015.2-.059.352-.153.43l-.841.692a1.875 1.875 0 00-.432 2.385l.922 1.597a1.875 1.875 0 002.282.818l1.019-.382c.115-.043.283-.031.45.082.312.214.641.405.985.57.182.088.277.228.297.35l.178 1.071c.151.904.933 1.567 1.85 1.567h1.844c.916 0 1.699-.663 1.85-1.567l.178-1.072c.02-.12.114-.26.297-.349.344-.165.673-.356.985-.57.167-.114.335-.125.45-.082l1.02.382a1.875 1.875 0 002.28-.819l.923-1.597a1.875 1.875 0 00-.432-2.385l-.84-.692c-.095-.078-.17-.229-.154-.43a7.614 7.614 0 000-1.139c-.016-.2.059-.352.153-.43l.84-.692c.708-.582.891-1.59.433-2.385l-.922-1.597a1.875 1.875 0 00-2.282-.818l-1.02.382c-.114.043-.282.031-.449-.083a7.49 7.49 0 00-.985-.57c-.183-.087-.277-.227-.297-.348l-.179-1.072a1.875 1.875 0 00-1.85-1.567h-1.843zM12 15.75a3.75 3.75 0 100-7.5 3.75 3.75 0 000 7.5z"
										clip-rule="evenodd" />
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
		</nav>
		<!-- End Navbar -->
		<div class="container-fluid py-4 px-5">
			<div class="row">
				<div class="col-md-12">
					<div class="d-md-flex align-items-center mb-3 mx-2">
						<div class="mb-md-0 mb-3">
							<h3 class="font-weight-bold mb-0">Gerenciamento de
								funcionários / Telefones</h3>
							<p class="mb-0">Todos os serviços relacionados a gestão dos
								funcionários da empresa</p>
						</div>
					</div>
				</div>
			</div>
			<hr class="my-0">
			<div class="row mt-3">

				<div class="card border shadow-xs mb-4">
					<div class="card border shadow-xs mb-4 mt-3">
						<div class="card-header border-bottom pb-0">
							<div class="d-sm-flex align-items-center">
								<div>
									<h6 class="font-weight-semibold text-lg mb-0" id="titulo-tab">Novo
										Telefone</h6>
									<p class="text-sm" id="info-tab">Informe o número do
										telefone para salva-lo no sistema</p>
								</div>

							</div>
						</div>

						<div class="mt-2 px-3">
							<form
								action="<%=request.getContextPath() + "/funcionario/telefone"%>"
								method="post" id="formTelefone">

								<input class="form-control" type="hidden"
									value="${funcionarioTelefone.id}" id="idFuncionario"
									name="idFuncionario">

								<div class="form-group">
									<label for="nome" class="form-control-label">Nome do
										funcionário</label> <input class="form-control" type="text"
										readonly="readonly" value="${funcionarioTelefone.nome}"
										id="nomeFuncionario">
								</div>

								<div class="form-group">
									<label for="idTelefone" class="form-control-label">Id
										telefone</label> <input class="form-control" type="number"
										readonly="readonly" value="${resposta.artefato.id}"
										id="idTelefone" name="idTelefone">
								</div>

								<div class="form-group">
									<label for="numero" class="form-control-label">Número</label> <input
										class="form-control" type="text" required="required"
										value="${resposta.artefato.numero}" id="numero" name="numero"
										onkeyup="formatarTelefone(event)">
								</div>

								<div class="card-body px-0 py-2">
									<div class="ms-auto d-flex align-items-center"
										id="btn-acoes-form">
										<button type="button" class="btn btn-success me-2"
											onclick="enviarForm()">Salvar</button>

										<button type="button" class="btn btn-info me-2"
											onclick="limparForm()">Novo telefone</button>
									</div>
								</div>

							</form>
						</div>


					</div>

					<div class="d-sm-flex align-items-center">
						<div>
							<h6 class="font-weight-semibold text-lg mb-2">Telefones
								cadastrados</h6>
						</div>
					</div>

					<div class="table-responsive p-0">
						<table
							class="table table-sm table-striped align-items-center mb-4"
							id="tabelaTelefones">
							<thead class="bg-gray-100">
								<tr>
									<th
										class="text-secondary text-xs font-weight-semibold opacity-7">Id</th>
									<th
										class="text-secondary text-xs font-weight-semibold opacity-7">Número</th>
									<th
										class="text-secondary opacity-7 text-xs font-weight-semibold opacity-7">Ações</th>
								</tr>
							</thead>
							<tbody>

								<c:choose>
									<c:when test="${not empty pagination.content}">
										<c:forEach items="${pagination.content}" var="t">
											<tr>
												<td>
													<div class="d-flex px-2 py-1">
														<h6 class="mb-0 text-sm font-weight-semibold">${t.id}</h6>
													</div>
												</td>
												<td>
													<div class="d-flex px-2 py-1">
														<div
															class="d-flex flex-column justify-content-center ms-1">
															<h6 class="mb-0 text-sm font-weight-semibold">${t.numero}</h6>
														</div>
													</div>
												</td>
												<td class="align-middle"><img onclick="editar(${t.id})"
													src="<%=contexto%>assets/img/editar.png" class="px-3"
													data-bs-toggle="tooltip" data-bs-title="Editar telefone">

													<img onclick="excluirTelefone(${t.id})"
													src="<%=contexto%>assets/img/excluir.png" class="px-3"
													data-bs-toggle="tooltip" data-bs-title="Excluir telefone">

												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>

										<tr>
											<td colspan="3">
												<div class="px-3 py-2" >
													<p class="mb-0">Nenhum registro encontrado!</p>
												</div>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>

							</tbody>
						</table>
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

						</div>
					</div>
				</div>
			</div>
		</footer>
	</main>
	<!-- Button trigger modal -->

	<div class="modal fade" id="msodalMsg" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLabel">Erro</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body" id="modalBody"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<!--   Core JS Files   -->
	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>

	<script src="<%=contexto%>assets/js/script-telefone.js"></script>
</body>

</html>