<%@page import="br.com.jdevtreinamentos.tf.model.Produto"%>
<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity"%>
<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.StatusResposta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String contexto = request.getContextPath() + "/view/admin/";

String valorPesquisa = (String) request.getAttribute("valorPesquisa");

String urlLink = "";

if (valorPesquisa != null && !valorPesquisa.trim().isEmpty()) {
	urlLink = request.getContextPath() + "/produto/pesquisar?valor=" + valorPesquisa + "&page=";

} else {
	urlLink = request.getContextPath() + "/produto?page=";
}
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
							class="opacity-5 text-dark" href="javascript:;">Produtos</a></li>

					</ol>
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
							<h3 class="font-weight-bold mb-0">Gerenciamento de Produtos</h3>
							<p class="mb-0">Todos os serviços relacionados a gestão dos
								produtos da empresa</p>
						</div>
					</div>
				</div>
			</div>
			<hr class="my-0">
			<div class="row mt-3">

				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<button class="nav-link active" id="nav-home-tab"
							data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
							role="tab" aria-controls="nav-home" aria-selected="true">Produtos</button>
						<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
							data-bs-target="#nav-profile" type="button" role="tab"
							aria-controls="nav-profile" aria-selected="false">Cadastro</button>
					</div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
					<div class="tab-pane show active" id="nav-home" role="tabpanel"
						aria-labelledby="nav-home-tab" tabindex="0">
						<div class="card border shadow-xs mb-4">
							<div class="card-header border-bottom pb-0">
								<div class="d-sm-flex align-items-center">
									<div>
										<h6 class="font-weight-semibold text-lg mb-2">Produtos
											Cadastrados</h6>
									</div>
								</div>
							</div>
							<div class="card-body px-0 py-0">
								<form
									action="<%=request.getContextPath() + "/produto/pesquisar"%>"
									id="formPesquisa">
									<form
										action="<%=request.getContextPath() + "/produto/pesquisar"%>"
										id="formPesquisa">
										<div class="input-group px-3 py-2">
											<span
												class="btn btn-sm btn-dark btn-icon d-flex align-items-center mb-0"
												onclick="enviarFormPesquisa()"> <svg
													xmlns="http://www.w3.org/2000/svg" width="16px"
													height="16px" fill="#FFF" viewBox="0 0 24 24"
													stroke-width="1.5" stroke="currentColor">
                    	<path stroke-linecap="round" stroke-linejoin="round"
														d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"></path>
                			</svg>
											</span> <input type="text" class="form-control px-2"
												placeholder="Search" value="${valorPesquisa}" name="valor">

											<a href="<%=request.getContextPath() + "/produto"%>"> <span
												class="input-group-text btn-dark btn-inner--text border-end-0 "
												style="background-color: #D10024;"> Limpar </span>
											</a>
										</div>
									</form>
								</form>

								<c:choose>
									<c:when test="${not empty pagination.content}">
										<div class="table-responsive p-0">
											<table
												class="table table-sm table-striped align-items-center mb-0">
												<thead class="bg-gray-100">
													<tr>
														<th
															class="text-secondary text-xs font-weight-semibold opacity-7">Descrição</th>
														<th
															class="text-center text-secondary text-xs font-weight-semibold opacity-7">Modelo</th>
														<th
															class="text-center text-secondary text-xs font-weight-semibold opacity-7">Marca</th>
														<th
															class="text-center text-secondary text-xs font-weight-semibold opacity-7">Valor</th>
														<th
															class="text-secondary opacity-7 text-xs font-weight-semibold opacity-7">Ações</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${pagination.content}" var="produto">
														<tr>
															<td>
																<div class="d-flex px-2 py-1">
																	<div
																		class="d-flex flex-column justify-content-center ms-1">
																		<h6 class="mb-0 text-sm font-weight-semibold">${produto.descricao}</h6>
																	</div>
																</div>
															</td>
															<td><span
																class="text-secondary text-sm font-weight-normal">${produto.modelo}</span>
															</td>
															<td class="align-middle text-center text-sm">
																<p class="text-sm text-dark font-weight-semibold mb-0">${produto.marca.nome}</p>

															</td>
															<td class="align-middle text-center"><span
																class="badge badge-sm border border-success text-sm font-weight-semibold mb-0 text-success bg-success">R$
																	${produto.valor}</span></td>
															<td class="align-middle"><img onclick="editar(this)"
																id="${produto.id}" src="<%=contexto%>assets/img/editar.png"
																class="px-3" data-bs-toggle="tooltip"
																data-bs-title="Editar produto"> <img
																onclick="excluir(${produto.id})"
																src="<%=contexto%>assets/img/excluir.png" class="px-3"
																data-bs-toggle="tooltip"
																data-bs-title="Excluir produto"></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="5">
												<div class="px-3 py-2">
													<p class="mb-0">Nenhum registro encontrado!</p>
												</div>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>

								<div class="row align-items-center border-top py-3 px-3">
									<div class="col-md-3">
										<p class="font-weight-semibold mb-3 text-dark text-sm">Página
											${pagination.pageable.pageNumber} de ${pagination.totalPages}</p>
									</div>

									<div class="col-md-9">
										<nav>
											<ul class="pagination justify-content-end">
												<c:choose>
													<c:when test="${pagination.pageable.pageNumber > 1}">
														<li class="page-item"><a
															href="<%=urlLink%>${pagination.pageable.pageNumber - 1}"
															class="btn btn-white mb-0">Anterior</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item"><a
															class="btn btn-white mb-0 disable-link">Anterior</a></li>
													</c:otherwise>
												</c:choose>

												<c:forEach var="i" begin="1" end="${pagination.totalPages}"
													step="1">
													<c:choose>
														<c:when test="${i eq pagination.pageable.pageNumber}">
															<li class="page-item active"><a
																class="btn btn-white mb-0 page-link">${i}</a></li>
														</c:when>
														<c:otherwise>
															<li class="page-item"><a
																class="btn btn-white mb-0 page-link"
																href="<%=urlLink%>${i}">${i}</a></li>
														</c:otherwise>
													</c:choose>
												</c:forEach>

												<c:choose>
													<c:when
														test="${pagination.pageable.pageNumber < pagination.totalPages}">
														<li class="page-item"><a class="btn btn-white mb-0"
															href="<%=urlLink%>${pagination.pageable.pageNumber + 1}">Próximo</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item"><a
															class="btn btn-white mb-0 disable-link">Próximo</a></li>
													</c:otherwise>
												</c:choose>
											</ul>
										</nav>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="tab-pane" id="nav-profile" role="tabpanel"
					aria-labelledby="nav-profile-tab" tabindex="0">
						<div class="card border shadow-xs mb-4">
						<div class="card-header border-bottom pb-0">
							<div class="d-sm-flex align-items-center">
								<div>
									<h6 class="font-weight-semibold text-lg mb-0" id="titulo-tab">Novo
										Produto</h6>
									<p class="text-sm" id="info-tab">Informe os dados do
										produto para cadastrá-lo no sistema</p>
								</div>
							</div>
						</div>

						<div class="mt-2 px-3">
							<form enctype="multipart/form-data"
								action="<%=request.getContextPath() + "/produto"%>"
								method="post" id="formProduto">

								<input type="hidden" value="${operacaoErro}" id="operacaoErro">
								<input type="hidden" value="${resposta.artefato.imagem}"
									id="imagemArmazenada" name="imagemArmazenada">

								<div class="form-group">
									<label for="id" class="form-control-label">Id</label> <input
										class="form-control" type="number" readonly="readonly"
										value="${resposta.artefato.id}" id="id" name="id">
								</div>
								<div class="form-group">
									<label for="descricao" class="form-control-label">Descrição</label>
									<input class="form-control" type="text" required="required"
										value="${resposta.artefato.descricao}" id="descricao"
										name="descricao">
								</div>

								<div class="form-group d-flex flex-column"
										id="groupPreviewImagem">
										<c:choose>
											<c:when test="${not empty resposta.artefato.imagem}">
												<img src="" class="rounded" alt="Imagem Produto"
													style="width: 200px; height: 200px; object-fit: cover;"
													id="box-img">
											</c:when>

											<c:otherwise>
												<img src="<%=contexto%>assets/img/produto.jpg"
													class="rounded" alt="Imagem produto" id="box-img"
													style="width: 200px; height: 200px; object-fit: cover;">
											</c:otherwise>
										</c:choose>
									</div>

								<div class="form-group">
									<label for="imagem" class="form-control-label">Imagem</label> <input
										class="form-control" type="file" accept="image/*"
										value="${resposta.artefato.imagem}" id="input-imagem"
										name="imagem" onchange="previewImage()">
								</div>

								<div class="form-group">
									<label for="modelo" class="form-control-label">Modelo</label> <input
										class="form-control" type="text" required="required"
										value="${resposta.artefato.modelo}" id="modelo" name="modelo">
								</div>

								<div class="form-group">
									<label for="marca">Marca</label> <select class="form-control"
										id="marca" name="marca" required="required">
										<c:forEach items="${marcas}" var="marca">
											<c:choose>
												<c:when test="${marca.id eq resposta.artefato.marca.id}">
													<option selected="selected" value="${marca.id}">${marca.nome}</option>
												</c:when>
												<c:otherwise>
													<option value="${marca.id}">${marca.nome}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>

								<div class="form-group">
									<label for="caracteristicas" class="form-control-label">Características</label>
									<textarea class="form-control" id="caracteristicas"
										name="caracteristicas" rows="3">${resposta.artefato.caracteristicas}</textarea>
								</div>

								<div class="form-group">
									<label for="valorProduto" class="form-control-label">Valor</label> <input
										class="form-control" type="text"
										required="required" value="${resposta.artefato.valor}"
										id="valorProduto" name="valorProduto">
								</div>

								<div class="form-group">
									<label for="desconto" class="form-control-label">Desconto (%)</label>
									<input class="form-control" type="text"
										value="${resposta.artefato.desconto}" id="desconto" required="required"
										name="desconto">
								</div>

								<div class="card-body px-0 py-2">
									<div class="ms-auto d-flex align-items-center"
										id="btn-acoes-form">
										<button type="button" class="btn btn-success me-2"
											onclick="enviarForm()">Salvar</button>
										<button type="button" class="btn btn-info me-2"
											onclick="restaurarTab()">Novo produto</button>
									</div>
								</div>

							</form>
						</div>
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

						</div>
					</div>
				</div>
			</div>
		</footer>
	</main>
	<!-- Button trigger modal -->

	<div class="modal fade" id="modalMsg" data-bs-backdrop="static"
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

	<script src="<%=contexto%>assets/js/script-produto.js"></script>
</body>

</html>