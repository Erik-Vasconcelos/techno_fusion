<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.ResponseEntity"%>
<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.StatusResposta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String contexto = request.getContextPath() + "/view/admin/";
%>

<c:set var="nomeUsuario" value="${fn:split(usuario.nome, ' ')[0]}" />

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
							class="opacity-5 text-dark" href="javascript:;">Relatórios</a></li>

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
							<h3 class="font-weight-bold mb-0">Relatório de marcas e
								produtos</h3>
							<p class="mb-0">Uma visão do produtos de cada marca de seus
								detalhes</p>
						</div>
					</div>
				</div>
			</div>
			<hr class="my-0">

			<div class="row mt-4">
				<div class="col-12">
					<div class="card shadow-xs border">
						<div class="card-header border-bottom pb-0">
							<form
								action="<%=request.getContextPath()%>/relatorio/marca-produtos"
								id="formRelatorio">

								<input type="hidden" name="acao" id="acao">

								<div class="d-sm-flex align-items-center mb-3">
									<div>
										<h6 class="font-weight-semibold text-lg mb-0">Relatório</h6>
										<p class="text-sm mb-sm-0 mb-2">Consulte ou realize o
											download dos dados</p>
									</div>
									<div class="ms-auto d-flex">
										<button type="button" class="btn btn-sm btn-white mb-0 me-2"
											onclick="verRelatorioMarcaProduto()">Ver relatório</button>
										<button type="button"
											class="btn btn-sm btn-dark btn-icon d-flex align-items-center mb-0 me-2">
											<span class="btn-inner--icon"> <svg width="16"
													height="16" xmlns="http://www.w3.org/2000/svg" fill="none"
													viewBox="0 0 24 24" stroke-width="1.5"
													stroke="currentColor" class="d-block me-2">
                        <path stroke-linecap="round"
														stroke-linejoin="round"
														d="M3 16.5v2.25A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75V16.5M16.5 12L12 16.5m0 0L7.5 12m4.5 4.5V3" />
                      </svg>
											</span> <span class="btn-inner--text"
												onclick="downloadRelatorioMarcaProdutoPdf()">Download
												PDF</span>
										</button>

										<button type="button"
											class="btn btn-sm btn-info btn-icon d-flex align-items-center mb-0">
											<span class="btn-inner--icon"> <svg width="16"
													height="16" xmlns="http://www.w3.org/2000/svg" fill="none"
													viewBox="0 0 24 24" stroke-width="1.5"
													stroke="currentColor" class="d-block me-2">
                        <path stroke-linecap="round"
														stroke-linejoin="round"
														d="M3 16.5v2.25A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75V16.5M16.5 12L12 16.5m0 0L7.5 12m4.5 4.5V3" />
                      </svg>
											</span> <span class="btn-inner--text"
												onclick="downloadRelatorioMarcaProdutoXls()">Download
												XLS</span>
										</button>

									</div>
								</div>
								<div class="form-group">
									<label for="marca">Marca</label> <select class="form-control"
										id="marca" name="marca">
										<option selected value="null">Todas</option>
										<c:forEach items="${marcas}" var="marca">
											<c:choose>
												<c:when test="${marca.id eq marcaSelecionada.id}">
													<option selected="selected" value="${marca.id}">${marca.nome}</option>
												</c:when>
												<c:otherwise>
													<option value="${marca.id}">${marca.nome}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>

								<div class="card-body px-0 py-0">
									<div class=" p-0 overflow-auto"
										style="max-height: 500px; display: block; width: 100%">

											<c:if test="${not empty relatorio}">

												<table
													class="table align-items-center justify-content-center mb-0 ">
													<thead class="bg-gray-100">
														<tr>
															<th colspan="6"
																class="text-secondary text-small font-weight-semibold opacity-7">Marca</th>
														</tr>
													</thead>
													<tbody>


														<c:forEach items="${relatorio}" var="m">
															<tr>
																<td colspan="5">
																	<div class="d-flex px-2 py-2">
																		<div class="my-auto">
																			<h6 class="mb-0 text-sm">${m.nome}</h6>
																		</div>
																	</div>
																</td>
																<td></td>
															</tr>
															<c:if test="${not empty m.produtos}">
																<thead class="bg-gray-100">
																	<tr>
																		<th colspan="2" align="center"
																			class="text-secondary text-xs font-weight-semibold opacity-7">Produto</th>
																		<th
																			class="text-secondary text-xs font-weight-semibold opacity-7">Modelo</th>
																		<th
																			class="text-secondary text-xs font-weight-semibold opacity-7">Valor</th>
																		<th
																			class="text-secondary text-xs font-weight-semibold opacity-7">Desconto</th>
																		<th
																			class="text-secondary text-xs font-weight-semibold opacity-7">Valor
																			final</th>
																	</tr>
																</thead>
															</c:if>
															<c:forEach items="${m.produtos}" var="p">
																<tr>
																	<td colspan="2" align="center">
																		<div class="px-2">
																			<div class="my-auto">
																				<h6 class="mb-0 text-sm">${p.descricao}</h6>
																			</div>
																		</div>
																	</td>
																	<td class="align-middle">
																		<div class="d-flex">
																			<div class="ms-2">
																				<p class="text-dark text-sm mb-0">${p.modelo}</p>
																			</div>
																		</div>
																	</td>
																	<td>
																		<p
																			class="text-sm font-weight-normal mb-0 valorParaFormatar">${p.valor}</p>
																	</td>
																	<td><span
																		class="text-sm font-weight-normal descontoParaFormatar">${p.desconto}</span>
																	</td>
																	<td>
																		<p
																			class="text-sm font-weight-normal mb-0 valorParaFormatar">${p.valor - (p.valor * (p.desconto/100))}</p>
																	</td>
																</tr>
															</c:forEach>
														</c:forEach>
													</tbody>
												</table>
											</c:if>
									</div>

								</div>
							</form>
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
										document
												.write(new Date().getFullYear())
									</script>
									Techno Fusion
								</div>
							</div>
						</div>
					</div>
				</footer>
			</div>
	</main>

	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>

	<script src="<%=contexto%>assets/js/script-rel-marca-produtos.js"></script>
</body>

</html>