package br.cefetrj.sca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetrj.sca.dominio.Aluno;
import br.cefetrj.sca.dominio.repositories.AlunoRepositorio;

@Service
public class HistoricoEscolarService {

	@Autowired
	private AlunoRepositorio alunoRepo;

	private Aluno getAlunoPorMatricula(String matricula) {
		if (matricula == null || matricula.trim().equals("")) {
			throw new IllegalArgumentException("Matrícula deve ser fornecida!");
		}

		Aluno aluno = null;

		try {
			aluno = alunoRepo.findAlunoByMatricula(matricula);
		} catch (Exception e) {
			throw new IllegalArgumentException("Aluno não encontrado ("
					+ matricula + ")", e);
		}

		return aluno;
	}

	public Aluno findAlunoByMatricula(String matricula) {
		return alunoRepo.findAlunoByMatricula(matricula);
	}

}