<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>

<template:mainLayout rootURL="${rootURL}" username="<%=UsuarioController.getCurrentUser().getNome()%>"
	contextPath="${pageContext.request.contextPath}" title="Sistema de Controle Acadêmico"  />
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SCA - Histórico Escolar</title>
</head>
<body>
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<form id="matriculaAluno" action="#">
						Insira a matrícula do aluno<br />
						<input type="text" id="matricula" placeholder="Ex: 1234567ADM" />
						<input type="submit" value="Enviar" />
					</form>
				</div>
			</div>
		</div>
	
</body>
</html>