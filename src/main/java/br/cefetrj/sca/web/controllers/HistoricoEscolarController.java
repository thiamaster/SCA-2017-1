package br.cefetrj.sca.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.cefetrj.sca.dominio.usuarios.Usuario;
import br.cefetrj.sca.service.CoordenacaoAtividadesComplementaresService;
import br.cefetrj.sca.service.HistoricoEscolarService;

@Controller
@SessionAttributes("roles")
@RequestMapping("/HistoricoEscolar")
public class HistoricoEscolarController {

	@Autowired
	HistoricoEscolarService service;

	@RequestMapping(value = "/{*}", method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute("error", "Erro: página não encontrada.");
		return "/login";
	}
	
	@RequestMapping(value = "/menuPrincipal")
	public String menuPrincipal(HttpSession session, Model model) {
		Usuario usr = UsuarioController.getCurrentUser();
		String matricula = usr.getMatricula();
		if (matricula != null) {
			return "/menuPrincipalView";
		} else {
			return "/login";
		}
	}
	
	@RequestMapping(value = { "/historicoEscolarAluno" }, method = RequestMethod.GET)
	public String getHistoricoEscolarAluno(HttpServletRequest request, HttpSession session, Model model){
		System.out.println("Entrou no Aluno");
		return "/menuPrincipalView";
	}
	
	@RequestMapping(value = "/historicoEscolarAdmin")
	public String alocaCoordenadoresAtividades(HttpServletRequest request, HttpSession session, Model model){
		System.out.println("Entrou no Admin");
		return "/historicoEscolar/requisitarMatriculaHistoricoView";
	}

}
