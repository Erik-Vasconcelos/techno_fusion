<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%
 	String contexto = request.getContextPath();
 %>
 <aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 bg-slate-900 fixed-start " id="sidenav-main">
    <div class="sidenav-header mt-2">
      <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none" aria-hidden="true" id="iconSidenav"></i>
      <a class="navbar-brand d-flex align-items-center m-0">
        <span class="font-weight-bold text-lg">Techno Fusion</span>
      </a>
    </div>
    <div class="collapse navbar-collapse px-4 w-auto" id="sidenav-collapse-main">
      <ul class="navbar-nav" id="ul-menu">
        <li class="nav-item">
          <a class="nav-link" href="<%=contexto%>/inicio" id="btnPaginaInicio">
             <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
              <img src="<%=contexto%>/view/admin/assets/img/inicio.png" alt="inicio" width="24">
            </div>
            <span class="nav-link-text ms-1">Início</span>
          </a>
        </li>
        
		<li class="nav-item">
          <a class="nav-link" href="<%=contexto%>/funcionario" id="btnPaginaFuncionarios">
            <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
              <img src="<%=contexto%>/view/admin/assets/img/usuarios.png" alt="funcionarios" width="24">
            </div>
            <span class="nav-link-text ms-1">Funcionários</span>
          </a>
        </li>
        
        <li class="nav-item">
          <a class="nav-link" href="<%=contexto%>/produto" id="btnPaginaProdutos" >
            <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
             <img src="<%=contexto%>/view/admin/assets/img/produto.png" alt="produtos" width="24">
            </div>
            <span class="nav-link-text ms-1">Produtos</span>
          </a>
        </li>
        
        <li class="nav-item">
          <a class="nav-link" href="<%=contexto%>/marca" id="btnPaginaMarcas">
            <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
              <img src="<%=contexto%>/view/admin/assets/img/marca.png" alt="marcas" width="24">
            </div>
            <span class="nav-link-text ms-1">Marcas</span>
          </a>
        </li>
         <li class="nav-item mt-2">
          <a class="d-flex align-items-center nav-link" id="btnPaginaRelatorios">
             <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
              <img src="<%=contexto%>/view/admin/assets/img/relatorio.png" alt="relatorios" width="24">
            </div>
            <span class="font-weight-normal text-md ms-2">Relatórios</span>
          </a>
        </li>
        <li class="nav-item border-start my-0 pt-2">
          <a class="nav-link position-relative ms-0 ps-2 py-2 " href="<%=contexto%>/relatorio/marca-produtos" id="btnRelatorioMarcaProduto">
            <span class="nav-link-text ms-1">Marcas e Produtos</span>
          </a>
        </li>
        
         <li class="nav-item mt-2">
          <a class="d-flex align-items-center nav-link" id="btnPaginaGraficos">
             <div class="icon icon-shape icon-sm px-0 text-center d-flex align-items-center justify-content-center">
              <img src="<%=contexto%>/view/admin/assets/img/grafico.png" alt="grafico" width="24">
            </div>
            <span class="font-weight-normal text-md ms-2">Gráficos</span>
          </a>
        </li>
        <li class="nav-item border-start my-0 pt-2">
          <a class="nav-link position-relative ms-0 ps-2 py-2 " href="<%=contexto%>/grafico/valor-medio-marca" id="btnGraficoValorMedio">
            <span class="nav-link-text ms-1">Valor médio por marca</span>
          </a>
        </li>
        
      </ul>
    </div>
    
  </aside>