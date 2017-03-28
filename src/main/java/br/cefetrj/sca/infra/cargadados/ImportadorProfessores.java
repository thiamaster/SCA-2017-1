package br.cefetrj.sca.infra.cargadados;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.cefetrj.sca.dominio.Professor;
import br.cefetrj.sca.dominio.repositories.ProfessorRepositorio;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 * Este importador realiza a carga de objetos <code>Professor</code>.
 * 
 * Se algum professor existente na planilha de entrada já tiver sido importado,
 * essa classe ainda sim solicita ao mecamismo de armazenamento a atualização
 * desse professor. Isso porque algum dado adicional sobre o professor pode ter
 * sido atualizado/inserido na planilha de entrada.
 *
 */

@Component
public class ImportadorProfessores {

	@Autowired
	ProfessorRepositorio professorRepositorio;

	String colunas[] = { "COD_DISCIPLINA", "NOME_DISCIPLINA", "COD_TURMA", "VAGAS_OFERECIDAS", "DIA_SEMANA",
			"HR_INICIO", "HR_FIM", "TIPO_AULA", "COD_CURSO", "NOME_UNIDADE", "NUM_VERSAO", "ITEM_TABELA",
			"PERIODO_ITEM", "ANO", "DIA_SEMANA_ITEM", "PERIODO", "DT_INICIO_PERIODO", "DT_FIM_PERIODO", "ID_TURMA",
			"NOME_DISCIPLINA_SUB", "MATR_EXTERNA", "NOME_DOCENTE", "ID" };

	/**
	 * Dicionário de pares <matrícula, nome> de cada professor encontrado na
	 * planilha de entrada.
	 */
	private HashMap<String, String> profs_nomes = new HashMap<>();

	public void run() {
		System.out.println("ImportadorProfessores.main()");
		try {
			/*String arquivoPlanilha = "./planilhas/turmas-ofertadas/11.02.03.99.19 (2016.1).xls";
			this.importarPlanilha(arquivoPlanilha);
<<<<<<< HEAD
			this.gravarDadosImportados();
			profs_nomes.clear();*/

			String arquivoPlanilha = "./planilhas/turmas-ofertadas/11.02.03.99.19 (2016.2).xls";
			this.importarPlanilha(arquivoPlanilha);

		} catch (BiffException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Feito!");
	}

	private StringBuilder gravarDadosImportados(StringBuilder response) {

		Set<String> profsIt = profs_nomes.keySet();
		int adicionados = 0;
		int atualizados = 0;
		for (String matrProfessor : profsIt) {
			Professor professor;
			try {
				professor = professorRepositorio.findProfessorByMatricula(matrProfessor);
			} catch (NoResultException e) {
				professor = null;
			}

			if (professor == null) {
				professorRepositorio.save(new Professor(matrProfessor, profs_nomes.get(matrProfessor)));
				adicionados++;
			} else {
				professorRepositorio.save(professor);
				atualizados++;
			}
		}
		response.append("Professores adicionados: " + adicionados + ";");
		response.append("Professores atualizados: " + atualizados + ";");

		return response;
	}

	private void importarPlanilha(String arquivoPlanilha) throws BiffException, IOException {
		File inputWorkbook = new File(arquivoPlanilha);
		importarPlanilha(inputWorkbook);
	}

	public String importarPlanilha(File inputWorkbook) throws BiffException, IOException {
		StringBuilder response = new StringBuilder();

		Workbook w;

		List<String> colunasList = Arrays.asList(colunas);
		response.append("Iniciando importação de professores...;");

		WorkbookSettings ws = new WorkbookSettings();
		ws.setEncoding("Cp1252");
		w = Workbook.getWorkbook(inputWorkbook, ws);
		Sheet sheet = w.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {

			/**
			 * Dados relativos aos docentes.
			 */
			String matriculaProfessor = sheet.getCell(colunasList.indexOf("MATR_EXTERNA"), i).getContents();
			String nomeProfessor = sheet.getCell(colunasList.indexOf("NOME_DOCENTE"), i).getContents();

			if (nomeProfessor.isEmpty()) {
				String nomeDisciplina = sheet.getCell(colunasList.indexOf("NOME_DISCIPLINA"), i).getContents();
				String codigoTurma = sheet.getCell(colunasList.indexOf("COD_TURMA"), i).getContents();

				String ano = sheet.getCell(colunasList.indexOf("ANO"), i).getContents();

				String periodo = sheet.getCell(colunasList.indexOf("PERIODO"), i).getContents();

				response.append("Turma sem professor alocado: " + codigoTurma + "(" + nomeDisciplina + ", " + ano
						+ " - " + periodo + ");");
			} else {
				profs_nomes.put(matriculaProfessor, nomeProfessor);
			}
		}

		response = gravarDadosImportados(response);

		response.append("Importação de professores finalizada.;");

		this.profs_nomes.clear();

		return response.toString();
	}
}