<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <header>
        <div id="header">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                        <div class="header-logo">
                            <h2 class="mb-0 mt-3 text-white">Techno Fusion</h2>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="header-search">
                            <form action="<%=request.getContextPath()%>/pesquisar" id="formPesquisa">
                                <input class="input-select input" placeholder="Pesquise aqui" name="valor" id="valor" value="${valorPesquisa}">
                                <button type="button" class="search-btn" onclick="enviarFormulario()">Buscar</button>
                            </form>
                        </div>
                    </div>

                    <ul class="header-links pull-right mt-4" style="margin-right:10px;">
                        <li><a href="<%=request.getContextPath()%>/login"><i class="fa fa-user-o"></i>Login funcionários</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>