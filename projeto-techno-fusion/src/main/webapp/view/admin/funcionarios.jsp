<%@page
	import="br.com.jdevtreinamentos.tf.controller.infra.StatusResposta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="fragmentos/head.jsp"></jsp:include>

<body class="g-sidenav-show  bg-gray-100">
	<jsp:include page="fragmentos/menu.jsp"></jsp:include>

	<main
		class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
		<!-- Navbar -->

		<c:if test="${not empty resposta.mensagem}">
			<c:if test="${resposta.status.codigo == 1}">
				<div class="alert alert-success">${resposta.mensagem}</div>
			</c:if>

			<c:if test="${resposta.status.codigo == 2}">
				<div class="alert alert-warning">${resposta.mensagem}</div>
			</c:if>

			<c:if test="${resposta.status.codigo == 3}">
				<div class="alert alert-danger">${resposta.mensagem}</div>
			</c:if>
		</c:if>

		<nav
			class="navbar navbar-main navbar-expand-lg mx-5 px-0 shadow-none rounded"
			id="navbarBlur" navbar-scroll="true">

			<div class="container-fluid py-1 px-2">
				<nav aria-label="breadcrumb">
					<ol
						class="breadcrumb bg-transparent mb-1 pb-0 pt-1 px-0 me-sm-6 me-5">
						<li class="breadcrumb-item text-sm"><a
							class="opacity-5 text-dark" href="javascript:;">inicio</a></li>
						<li class="breadcrumb-item text-sm text-dark active"
							aria-current="page">inicio</li>
					</ol>
					<h6 class="font-weight-bold mb-0">Inicio</h6>
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
						<li class="nav-item dropdown pe-2 d-flex align-items-center">
							<a href="javascript:;" class="nav-link text-body p-0"
							id="dropdownMenuButton" data-bs-toggle="dropdown"
							aria-expanded="false"> <svg height="16" width="16"
									xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
									fill="currentColor" class="cursor-pointers">
                    <path fill-rule="evenodd"
										d="M5.25 9a6.75 6.75 0 0113.5 0v.75c0 2.123.8 4.057 2.118 5.52a.75.75 0 01-.297 1.206c-1.544.57-3.16.99-4.831 1.243a3.75 3.75 0 11-7.48 0 24.585 24.585 0 01-4.831-1.244.75.75 0 01-.298-1.205A8.217 8.217 0 005.25 9.75V9zm4.502 8.9a2.25 2.25 0 104.496 0 25.057 25.057 0 01-4.496 0z"
										clip-rule="evenodd" />
                  </svg>
						</a>
							<ul class="dropdown-menu  dropdown-menu-end  px-2 py-3 me-sm-n4"
								aria-labelledby="dropdownMenuButton">
								<li class="mb-2"><a class="dropdown-item border-radius-md"
									href="javascript:;">
										<div class="d-flex py-1">
											<div class="my-auto">
												<img src="assets/img/team-2.jpg"
													class="avatar avatar-sm border-radius-sm  me-3 ">
											</div>
											<div class="d-flex flex-column justify-content-center">
												<h6 class="text-sm font-weight-normal mb-1">
													<span class="font-weight-bold">New message</span> from Laur
												</h6>
												<p
													class="text-xs text-secondary mb-0 d-flex align-items-center ">
													<i class="fa fa-clock opacity-6 me-1"></i> 13 minutes ago
												</p>
											</div>
										</div>
								</a></li>
								<li class="mb-2"><a class="dropdown-item border-radius-md"
									href="javascript:;">
										<div class="d-flex py-1">
											<div class="my-auto">
												<img src="assets/img/small-logos/logo-google.svg"
													class="avatar avatar-sm border-radius-sm bg-gradient-dark p-2  me-3 ">
											</div>
											<div class="d-flex flex-column justify-content-center">
												<h6 class="text-sm font-weight-normal mb-1">
													<span class="font-weight-bold">New report</span> by Google
												</h6>
												<p
													class="text-xs text-secondary mb-0 d-flex align-items-center ">
													<i class="fa fa-clock opacity-6 me-1"></i> 1 day
												</p>
											</div>
										</div>
								</a></li>
								<li><a class="dropdown-item border-radius-md"
									href="javascript:;">
										<div class="d-flex py-1">
											<div
												class="avatar avatar-sm border-radius-sm bg-slate-800  me-3  my-auto">
												<svg width="12px" height="12px" viewBox="0 0 43 36"
													version="1.1" xmlns="http://www.w3.org/2000/svg"
													xmlns:xlink="http://www.w3.org/1999/xlink">
                            <title>credit-card</title>
                            <g stroke="none" stroke-width="1"
														fill="none" fill-rule="evenodd">
                              <g
														transform="translate(-2169.000000, -745.000000)"
														fill="#FFFFFF" fill-rule="nonzero">
                                <g
														transform="translate(1716.000000, 291.000000)">
                                  <g
														transform="translate(453.000000, 454.000000)">
                                    <path class="color-background"
														d="M43,10.7482083 L43,3.58333333 C43,1.60354167 41.3964583,0 39.4166667,0 L3.58333333,0 C1.60354167,0 0,1.60354167 0,3.58333333 L0,10.7482083 L43,10.7482083 Z"
														opacity="0.593633743"></path>
                                    <path class="color-background"
														d="M0,16.125 L0,32.25 C0,34.2297917 1.60354167,35.8333333 3.58333333,35.8333333 L39.4166667,35.8333333 C41.3964583,35.8333333 43,34.2297917 43,32.25 L43,16.125 L0,16.125 Z M19.7083333,26.875 L7.16666667,26.875 L7.16666667,23.2916667 L19.7083333,23.2916667 L19.7083333,26.875 Z M35.8333333,26.875 L28.6666667,26.875 L28.6666667,23.2916667 L35.8333333,23.2916667 L35.8333333,26.875 Z"></path>
                                  </g>
                                </g>
                              </g>
                            </g>
                          </svg>
											</div>
											<div class="d-flex flex-column justify-content-center">
												<h6 class="text-sm font-weight-normal mb-1">Payment
													successfully completed</h6>
												<p
													class="text-xs text-secondary d-flex align-items-center mb-0 ">
													<i class="fa fa-clock opacity-6 me-1"></i> 2 days
												</p>
											</div>
										</div>
								</a></li>
							</ul>
						</li>
						<li class="nav-item ps-2 d-flex align-items-center"><a
							href="javascript:;" class="nav-link text-body p-0"> <img
								src="assets/img/team-2.jpg" class="avatar avatar-sm"
								alt="avatar" />
						</a></li>
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
								funcionários</h3>
							<p class="mb-0">Todos os serviços relacionados a gestão dos
								funcionários da empresa</p>
						</div>
						<button type="button"
							class="btn btn-sm btn-white btn-icon d-flex align-items-center mb-0 ms-md-auto mb-sm-0 mb-2 me-2">
							<span class="btn-inner--icon"> <span
								class="p-1 bg-success rounded-circle d-flex ms-auto me-2">
									<span class="visually-hidden">New</span>
							</span>
							</span> <span class="btn-inner--text">Messages</span>
						</button>
						<button type="button"
							class="btn btn-sm btn-dark btn-icon d-flex align-items-center mb-0">
							<span class="btn-inner--icon"> <svg width="16" height="16"
									xmlns="http://www.w3.org/2000/svg" fill="none"
									viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
									class="d-block me-2">
                    <path stroke-linecap="round" stroke-linejoin="round"
										d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99" />
                  </svg>
							</span> <span class="btn-inner--text">Sync</span>
						</button>
					</div>
				</div>
			</div>
			<hr class="my-0">
			<div class="row mt-3">
				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<button class="nav-link active" id="nav-home-tab"
							data-bs-toggle="tab" data-bs-target="#tab-cadastro" type="button"
							role="tab" aria-controls="nav-home" aria-selected="true">Cadastro</button>
						<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
							data-bs-target="#tab-funcionarios" type="button" role="tab"
							aria-controls="nav-profile" aria-selected="false">Funcionários</button>
					</div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
					<div class="col-12 tab-pane fade show active" id="tab-cadastro"
						role="tabpanel" aria-labelledby="cadastro-tab">
						<div class="card border shadow-xs mb-4">
							<div class="card-header border-bottom pb-0">
								<div class="d-sm-flex align-items-center">
									<div>
										<h6 class="font-weight-semibold text-lg mb-0">Novo
											funcionário</h6>
										<p class="text-sm">Informe os dados do funcionário para
											cadastrá-lo no sistema</p>
									</div>

								</div>
							</div>

							<div class="mt-2 px-3">
								<form action="<%=request.getContextPath() + "/funcionario"%>"
									method="post">

									<div class="form-group">

										<label for="example-text-input" class="form-control-label">Id</label>
										<input class="form-control" type="number" readonly="readonly"
											value="" id="id" name="id">
									</div>
									<div class="form-group">
										<label for="example-text-input" class="form-control-label">Nome</label>
										<input class="form-control" type="text" required="required"
											value="${resposta.artefato.nome}" id="nome" name="nome">
									</div>

									<label class="form-control-label">Sexo</label>
									<div class="form-check mb-3">

										<%int count = 1;%>
										<c:forEach items="${tipoSexo}" var="s">
											<input class="form-check-input" type="radio"
												value="${s.name()}" required="required" name="sexo"
												id="radioSexo<%=count%>"
												<%if(count == 1){ out.print("checked"); count ++;}%>>
											<label class="custom-control-label"
												for="radioSexo<%=count++%>">${s.descricao}</label>
											<br>
										</c:forEach>

									</div>

									<div class="form-group">
										<label for="example-date-input" class="form-control-label">Data
											de nascimento</label> <input class="form-control" type="date"
											value="${resposta.artefato.dataNascimento}"
											required="required" id="dataNascimento" name="dataNascimento">
									</div>

									<div class="form-group">
										<label for="example-email-input" class="form-control-label">Email</label>
										<input class="form-control" type="email" required="required"
											value="${resposta.artefato.email}" id="email" name="email">
									</div>

									<div class="form-group">
										<label for="example-text-input" class="form-control-label">Salário</label>
										<input class="form-control" type="text" required="required"
											value="${resposta.artefato.salario}" id="salario"
											name="salario">
									</div>

									<div class="form-group">
										<label for="exampleFormControlSelect1">Perfil</label> <select
											class="form-control" id="perfil" name="perfil"
											required="required">
											<c:forEach items="${perfilFuncionario}" var="p">
												<option value="${p.name()}">${p.descricao}</option>
											</c:forEach>
										</select>
									</div>

									<div class="form-group">
										<label for="example-text-input" class="form-control-label">Login</label>
										<input class="form-control" type="text" required="required"
											value="${resposta.artefato.login}" id="login" name="login"
											minlength="5" maxlength="8">
									</div>

									<div class="form-group">
										<label for="senha" class="form-control-label">Senha</label> <input
											class="form-control" type="text" readonly="readonly"
											value="A senha é gereda automáticamente: email + login (exemplo@gmail.com20230415)"
											id="senha">
									</div>

									<div class="card-body px-0 py-2">
										<div class="ms-auto">
											<input type="submit"
												class="btn btn-sm btn-dark btn-icon d-flex align-items-center me-2"
												value="Salvar">
										</div>
									</div>

								</form>
							</div>


						</div>
					</div>
				</div>

				<div class="col-12 tab-pane fade" id="tab-funcionarios"
					role="tabpanel" aria-labelledby="funcionarios-tab">
					<div class="card border shadow-xs mb-4">
						<div class="card-header border-bottom pb-0">
							<div class="d-sm-flex align-items-center">
								<div>
									<h6 class="font-weight-semibold text-lg mb-0">Novo
										funcionario</h6>
									<p class="text-sm">Informe os dados do funcionario para
										cadastrá-lo no sistema</p>
								</div>
								<div class="ms-auto d-flex">
									<button type="button" class="btn btn-sm btn-white me-2">
										View all</button>
									<button type="button"
										class="btn btn-sm btn-dark btn-icon d-flex align-items-center me-2">
										<span class="btn-inner--icon"> <svg width="16"
												height="16" xmlns="http://www.w3.org/2000/svg"
												viewBox="0 0 24 24" fill="currentColor" class="d-block me-2">
                            <path
													d="M6.25 6.375a4.125 4.125 0 118.25 0 4.125 4.125 0 01-8.25 0zM3.25 19.125a7.125 7.125 0 0114.25 0v.003l-.001.119a.75.75 0 01-.363.63 13.067 13.067 0 01-6.761 1.873c-2.472 0-4.786-.684-6.76-1.873a.75.75 0 01-.364-.63l-.001-.122zM19.75 7.5a.75.75 0 00-1.5 0v2.25H16a.75.75 0 000 1.5h2.25v2.25a.75.75 0 001.5 0v-2.25H22a.75.75 0 000-1.5h-2.25V7.5z" />
                          </svg>
										</span> <span class="btn-inner--text">Add member</span>
									</button>
								</div>
							</div>
						</div>
						<div class="card-body px-0 py-0">
							<div class="border-bottom py-3 px-3 d-sm-flex align-items-center">
								<div class="btn-group" role="group"
									aria-label="Basic radio toggle button group">
									<input type="radio" class="btn-check" name="btnradiotable"
										id="btnradiotable1" autocomplete="off" checked> <label
										class="btn btn-white px-3 mb-0" for="btnradiotable1">All</label>
									<input type="radio" class="btn-check" name="btnradiotable"
										id="btnradiotable2" autocomplete="off"> <label
										class="btn btn-white px-3 mb-0" for="btnradiotable2">Monitored</label>
									<input type="radio" class="btn-check" name="btnradiotable"
										id="btnradiotable3" autocomplete="off"> <label
										class="btn btn-white px-3 mb-0" for="btnradiotable3">Unmonitored</label>
								</div>
								<div class="input-group w-sm-25 ms-auto">
									<span class="input-group-text text-body"> <svg
											xmlns="http://www.w3.org/2000/svg" width="16px" height="16px"
											fill="none" viewBox="0 0 24 24" stroke-width="1.5"
											stroke="currentColor">
                          <path stroke-linecap="round"
												stroke-linejoin="round"
												d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"></path>
                        </svg>
									</span> <input type="text" class="form-control" placeholder="Search">
								</div>
							</div>
							<div class="table-responsive p-0">
								<table class="table align-items-center mb-0">
									<thead class="bg-gray-100">
										<tr>
											<th
												class="text-secondary text-xs font-weight-semibold opacity-7">Member</th>
											<th
												class="text-secondary text-xs font-weight-semibold opacity-7 ps-2">Function</th>
											<th
												class="text-center text-secondary text-xs font-weight-semibold opacity-7">Status</th>
											<th
												class="text-center text-secondary text-xs font-weight-semibold opacity-7">Employed</th>
											<th class="text-secondary opacity-7"></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/team-2.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user1">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">John
															Michael</h6>
														<p class="text-sm text-secondary mb-0">john@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Manager</p>
												<p class="text-sm text-secondary mb-0">Organization</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-success text-success bg-success">Online</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">23/04/18</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/team-3.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user2">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">Alexa
															Liras</h6>
														<p class="text-sm text-secondary mb-0">alexa@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Programator</p>
												<p class="text-sm text-secondary mb-0">Developer</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-secondary text-secondary bg-secondary">Offline</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">11/01/19</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/team-1.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user3">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">Laurent
															Perrier</h6>
														<p class="text-sm text-secondary mb-0">laurent@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Executive</p>
												<p class="text-sm text-secondary mb-0">Projects</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-success text-success bg-success">Online</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">19/09/17</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/marie.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user4">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">Michael
															Levi</h6>
														<p class="text-sm text-secondary mb-0">michael@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Programator</p>
												<p class="text-sm text-secondary mb-0">Developer</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-success text-success bg-success">Online</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">24/12/08</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/team-5.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user5">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">Richard
															Gran</h6>
														<p class="text-sm text-secondary mb-0">richard@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Manager</p>
												<p class="text-sm text-secondary mb-0">Executive</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-secondary text-secondary bg-secondary">Offline</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">04/10/21</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
										<tr>
											<td>
												<div class="d-flex px-2 py-1">
													<div class="d-flex align-items-center">
														<img src="assets/img/team-6.jpg"
															class="avatar avatar-sm rounded-circle me-2" alt="user6">
													</div>
													<div class="d-flex flex-column justify-content-center ms-1">
														<h6 class="mb-0 text-sm font-weight-semibold">Miriam
															Eric</h6>
														<p class="text-sm text-secondary mb-0">miriam@creative-tim.com</p>
													</div>
												</div>
											</td>
											<td>
												<p class="text-sm text-dark font-weight-semibold mb-0">Programtor</p>
												<p class="text-sm text-secondary mb-0">Developer</p>
											</td>
											<td class="align-middle text-center text-sm"><span
												class="badge badge-sm border border-secondary text-secondary bg-secondary">Offline</span>
											</td>
											<td class="align-middle text-center"><span
												class="text-secondary text-sm font-weight-normal">14/09/20</span>
											</td>
											<td class="align-middle"><a href="javascript:;"
												class="text-secondary font-weight-bold text-xs"
												data-bs-toggle="tooltip" data-bs-title="Edit user"> <svg
														width="14" height="14" viewBox="0 0 15 16" fill="none"
														xmlns="http://www.w3.org/2000/svg">
                                <path
															d="M11.2201 2.02495C10.8292 1.63482 10.196 1.63545 9.80585 2.02636C9.41572 2.41727 9.41635 3.05044 9.80726 3.44057L11.2201 2.02495ZM12.5572 6.18502C12.9481 6.57516 13.5813 6.57453 13.9714 6.18362C14.3615 5.79271 14.3609 5.15954 13.97 4.7694L12.5572 6.18502ZM11.6803 1.56839L12.3867 2.2762L12.3867 2.27619L11.6803 1.56839ZM14.4302 4.31284L15.1367 5.02065L15.1367 5.02064L14.4302 4.31284ZM3.72198 15V16C3.98686 16 4.24091 15.8949 4.42839 15.7078L3.72198 15ZM0.999756 15H-0.000244141C-0.000244141 15.5523 0.447471 16 0.999756 16L0.999756 15ZM0.999756 12.2279L0.293346 11.5201C0.105383 11.7077 -0.000244141 11.9624 -0.000244141 12.2279H0.999756ZM9.80726 3.44057L12.5572 6.18502L13.97 4.7694L11.2201 2.02495L9.80726 3.44057ZM12.3867 2.27619C12.7557 1.90794 13.3549 1.90794 13.7238 2.27619L15.1367 0.860593C13.9869 -0.286864 12.1236 -0.286864 10.9739 0.860593L12.3867 2.27619ZM13.7238 2.27619C14.0917 2.64337 14.0917 3.23787 13.7238 3.60504L15.1367 5.02064C16.2875 3.8721 16.2875 2.00913 15.1367 0.860593L13.7238 2.27619ZM13.7238 3.60504L3.01557 14.2922L4.42839 15.7078L15.1367 5.02065L13.7238 3.60504ZM3.72198 14H0.999756V16H3.72198V14ZM1.99976 15V12.2279H-0.000244141V15H1.99976ZM1.70617 12.9357L12.3867 2.2762L10.9739 0.86059L0.293346 11.5201L1.70617 12.9357Z"
															fill="#64748B" />
                              </svg>
											</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="border-top py-3 px-3 d-flex align-items-center">
								<p class="font-weight-semibold mb-0 text-dark text-sm">Page
									1 of 10</p>
								<div class="ms-auto">
									<button class="btn btn-sm btn-white mb-0">Previous</button>
									<button class="btn btn-sm btn-white mb-0">Next</button>
								</div>
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
							Corporate UI by <a href="https://www.creative-tim.com"
								class="text-secondary" target="_blank">Creative Tim</a>.
						</div>
					</div>
					<div class="col-lg-6">
						<ul
							class="nav nav-footer justify-content-center justify-content-lg-end">
							<li class="nav-item"><a href="https://www.creative-tim.com"
								class="nav-link text-xs text-muted" target="_blank">Creative
									Tim</a></li>
							<li class="nav-item"><a
								href="https://www.creative-tim.com/presentation"
								class="nav-link text-xs text-muted" target="_blank">About Us</a>
							</li>
							<li class="nav-item"><a
								href="https://www.creative-tim.com/blog"
								class="nav-link text-xs text-muted" target="_blank">Blog</a></li>
							<li class="nav-item"><a
								href="https://www.creative-tim.com/license"
								class="nav-link text-xs pe-0 text-muted" target="_blank">License</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</footer>
		</div>
	</main>
	<div class="fixed-plugin">
		<a class="fixed-plugin-button text-dark position-fixed px-3 py-2">
			<i class="fa fa-cog py-2"></i>
		</a>
		<div class="card shadow-lg ">
			<div class="card-header pb-0 pt-3 ">
				<div class="float-start">
					<h5 class="mt-3 mb-0">Corporate UI Configurator</h5>
					<p>See our Início options.</p>
				</div>
				<div class="float-end mt-4">
					<button
						class="btn btn-link text-dark p-0 fixed-plugin-close-button">
						<i class="fa fa-close"></i>
					</button>
				</div>
				<!-- End Toggle Button -->
			</div>
			<hr class="horizontal dark my-1">
			<div class="card-body pt-sm-3 pt-0">
				<!-- Sidebar Backgrounds -->
				<div>
					<h6 class="mb-0">Sidebar Colors</h6>
				</div>
				<a href="javascript:void(0)" class="switch-trigger background-color">
					<div class="badge-colors my-2 text-start">
						<span class="badge filter bg-gradient-primary active"
							data-color="primary" onclick="sidebarColor(this)"></span> <span
							class="badge filter bg-gradient-info" data-color="info"
							onclick="sidebarColor(this)"></span> <span
							class="badge filter bg-gradient-success" data-color="success"
							onclick="sidebarColor(this)"></span> <span
							class="badge filter bg-gradient-warning" data-color="warning"
							onclick="sidebarColor(this)"></span> <span
							class="badge filter bg-gradient-danger" data-color="danger"
							onclick="sidebarColor(this)"></span>
					</div>
				</a>
				<!-- Sidenav Type -->
				<div class="mt-3">
					<h6 class="mb-0">Sidenav Type</h6>
					<p class="text-sm">Choose between 2 different sidenav types.</p>
				</div>
				<div class="d-flex">
					<button class="btn bg-gradient-primary w-100 px-3 mb-2 active"
						data-class="bg-slate-900" onclick="sidebarType(this)">Dark</button>
					<button class="btn bg-gradient-primary w-100 px-3 mb-2 ms-2"
						data-class="bg-white" onclick="sidebarType(this)">White</button>
				</div>
				<p class="text-sm d-xl-none d-block mt-2">You can change the
					sidenav type just on desktop view.</p>
				<!-- Navbar Fixed -->
				<div class="mt-3">
					<h6 class="mb-0">Navbar Fixed</h6>
				</div>
				<div class="form-check form-switch ps-0">
					<input class="form-check-input mt-1 ms-auto" type="checkbox"
						id="navbarFixed" onclick="navbarFixed(this)">
				</div>
				<hr class="horizontal dark my-sm-4">
				<a class="btn bg-gradient-dark w-100"
					href="https://www.creative-tim.com/product/corporate-ui-dashboard">Free
					Download</a> <a class="btn btn-outline-dark w-100"
					href="https://www.creative-tim.com/learning-lab/bootstrap/license/corporate-ui-dashboard">View
					documentation</a>
				<div class="w-100 text-center">
					<a class="github-button"
						href="https://github.com/creativetimofficial/corporate-ui-dashboard"
						data-icon="octicon-star" data-size="large" data-show-count="true"
						aria-label="Star creativetimofficial/corporate-ui-dashboard on GitHub">Star</a>
					<h6 class="mt-3">Thank you for sharing!</h6>
					<a
						href="https://twitter.com/intent/tweet?text=Check%20Corporate%20UI%20Dashboard%20made%20by%20%40CreativeTim%20%23webdesign%20%23dashboard%20%23bootstrap5&amp;url=https%3A%2F%2Fwww.creative-tim.com%2Fproduct%2Fcorporate-ui-dashboard"
						class="btn btn-dark mb-0 me-2" target="_blank"> <i
						class="fab fa-twitter me-1" aria-hidden="true"></i> Tweet
					</a> <a
						href="https://www.facebook.com/sharer/sharer.php?u=https://www.creative-tim.com/product/corporate-ui-dashboard"
						class="btn btn-dark mb-0 me-2" target="_blank"> <i
						class="fab fa-facebook-square me-1" aria-hidden="true"></i> Share
					</a>
				</div>
			</div>
		</div>
	</div>
	<!--   Core JS Files   -->
	<jsp:include page="fragmentos/scripts.jsp"></jsp:include>

	<script>
	$("#nome").focus();

    $(function() {
        $('#salario').maskMoney({
			prefix:'R$ ',
			thousands: '.',
			decimal: ','
         });
      })
    
    if (document.getElementsByClassName('mySwiper')) {
      var swiper = new Swiper(".mySwiper", {
        effect: "cards",
        grabCursor: true,
        initialSlide: 1,
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
      });
    };

    var ctx = document.getElementById("chart-bars").getContext("2d");

    new Chart(ctx, {
      type: "bar",
      data: {
        labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
        datasets: [{
            label: "Sales",
            tension: 0.4,
            borderWidth: 0,
            borderSkipped: false,
            backgroundColor: "#2ca8ff",
            data: [450, 200, 100, 220, 500, 100, 400, 230, 500, 200],
            maxBarThickness: 6
          },
          {
            label: "Sales",
            tension: 0.4,
            borderWidth: 0,
            borderSkipped: false,
            backgroundColor: "#7c3aed",
            data: [200, 300, 200, 420, 400, 200, 300, 430, 400, 300],
            maxBarThickness: 6
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
          },
          tooltip: {
            backgroundColor: '#fff',
            titleColor: '#1e293b',
            bodyColor: '#1e293b',
            borderColor: '#e9ecef',
            borderWidth: 1,
            usePointStyle: true
          }
        },
        interaction: {
          intersect: false,
          mode: 'index',
        },
        scales: {
          y: {
            stacked: true,
            grid: {
              drawBorder: false,
              display: true,
              drawOnChartArea: true,
              drawTicks: false,
              borderDash: [4, 4],
            },
            ticks: {
              beginAtZero: true,
              padding: 10,
              font: {
                size: 12,
                family: "Noto Sans",
                style: 'normal',
                lineHeight: 2
              },
              color: "#64748B"
            },
          },
          x: {
            stacked: true,
            grid: {
              drawBorder: false,
              display: false,
              drawOnChartArea: false,
              drawTicks: false
            },
            ticks: {
              font: {
                size: 12,
                family: "Noto Sans",
                style: 'normal',
                lineHeight: 2
              },
              color: "#64748B"
            },
          },
        },
      },
    });


    var ctx2 = document.getElementById("chart-line").getContext("2d");

    var gradientStroke1 = ctx2.createLinearGradient(0, 230, 0, 50);

    gradientStroke1.addColorStop(1, 'rgba(45,168,255,0.2)');
    gradientStroke1.addColorStop(0.2, 'rgba(45,168,255,0.0)');
    gradientStroke1.addColorStop(0, 'rgba(45,168,255,0)'); //blue colors

    var gradientStroke2 = ctx2.createLinearGradient(0, 230, 0, 50);

    gradientStroke2.addColorStop(1, 'rgba(119,77,211,0.4)');
    gradientStroke2.addColorStop(0.7, 'rgba(119,77,211,0.1)');
    gradientStroke2.addColorStop(0, 'rgba(119,77,211,0)'); //purple colors

    new Chart(ctx2, {
      plugins: [{
        beforeInit(chart) {
          const originalFit = chart.legend.fit;
          chart.legend.fit = function fit() {
            originalFit.bind(chart.legend)();
            this.height += 40;
          }
        },
      }],
      type: "line",
      data: {
        labels: ["Aug 18", "Aug 19", "Aug 20", "Aug 21", "Aug 22", "Aug 23", "Aug 24", "Aug 25", "Aug 26", "Aug 27", "Aug 28", "Aug 29", "Aug 30", "Aug 31", "Sept 01", "Sept 02", "Sept 03", "Aug 22", "Sept 04", "Sept 05", "Sept 06", "Sept 07", "Sept 08", "Sept 09"],
        datasets: [{
            label: "Volume",
            tension: 0,
            borderWidth: 2,
            pointRadius: 3,
            borderColor: "#2ca8ff",
            pointBorderColor: '#2ca8ff',
            pointBackgroundColor: '#2ca8ff',
            backgroundColor: gradientStroke1,
            fill: true,
            data: [2828, 1291, 3360, 3223, 1630, 980, 2059, 3092, 1831, 1842, 1902, 1478, 1123, 2444, 2636, 2593, 2885, 1764, 898, 1356, 2573, 3382, 2858, 4228],
            maxBarThickness: 6

          },
          {
            label: "Trade",
            tension: 0,
            borderWidth: 2,
            pointRadius: 3,
            borderColor: "#832bf9",
            pointBorderColor: '#832bf9',
            pointBackgroundColor: '#832bf9',
            backgroundColor: gradientStroke2,
            fill: true,
            data: [2797, 2182, 1069, 2098, 3309, 3881, 2059, 3239, 6215, 2185, 2115, 5430, 4648, 2444, 2161, 3018, 1153, 1068, 2192, 1152, 2129, 1396, 2067, 1215, 712, 2462, 1669, 2360, 2787, 861],
            maxBarThickness: 6
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true,
            position: 'top',
            align: 'end',
            labels: {
              boxWidth: 6,
              boxHeight: 6,
              padding: 20,
              pointStyle: 'circle',
              borderRadius: 50,
              usePointStyle: true,
              font: {
                weight: 400,
              },
            },
          },
          tooltip: {
            backgroundColor: '#fff',
            titleColor: '#1e293b',
            bodyColor: '#1e293b',
            borderColor: '#e9ecef',
            borderWidth: 1,
            pointRadius: 2,
            usePointStyle: true,
            boxWidth: 8,
          }
        },
        interaction: {
          intersect: false,
          mode: 'index',
        },
        scales: {
          y: {
            grid: {
              drawBorder: false,
              display: true,
              drawOnChartArea: true,
              drawTicks: false,
              borderDash: [4, 4]
            },
            ticks: {
              callback: function(value, index, ticks) {
                return parseInt(value).toLocaleString() + ' EUR';
              },
              display: true,
              padding: 10,
              color: '#b2b9bf',
              font: {
                size: 12,
                family: "Noto Sans",
                style: 'normal',
                lineHeight: 2
              },
              color: "#64748B"
            }
          },
          x: {
            grid: {
              drawBorder: false,
              display: false,
              drawOnChartArea: false,
              drawTicks: false,
              borderDash: [4, 4]
            },
            ticks: {
              display: true,
              color: '#b2b9bf',
              padding: 20,
              font: {
                size: 12,
                family: "Noto Sans",
                style: 'normal',
                lineHeight: 2
              },
              color: "#64748B"
            }
          },
        },
      },
    });
  </script>
	<script>


    var win = navigator.platform.indexOf('Win') > -1;
    if (win && document.querySelector('#sidenav-scrollbar')) {
      var options = {
        damping: '0.5'
      }
      Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
    }
  </script>
</body>

</html>