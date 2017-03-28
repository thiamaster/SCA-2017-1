package br.cefetrj.sca.dominio.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.cefetrj.sca.dominio.Disciplina;
import br.cefetrj.sca.dominio.VersaoCurso;

public interface DisciplinaRepositorio extends
		JpaRepository<Disciplina, Serializable> {

	Disciplina findDisciplinaById(Long idDisciplina);

	@Query("from Disciplina d where d.codigo = ?1 "
			+ "and d.versaoCurso.curso.sigla = ?2 "
			+ "and d.versaoCurso.numero = ?3")
	Disciplina findByCodigoEmVersaoCurso(String codigoDisciplina,
			String siglaCurso, String numeroVersaoCurso);

	@Query("from Disciplina d where d.codigo = ?1 and d.versaoCurso = ?2")
	Disciplina findByCodigoEmVersaoCurso(String codigoDisciplina,
			VersaoCurso versaoCurso);

	@Query("from Disciplina d where d.nome = ?1 "
			+ "and d.versaoCurso.curso.sigla = ?2"
			+ " and d.versaoCurso.numero = ?3")
	Disciplina findByNomeEmVersaoCurso(String nomeDisciplina,
			String siglaCurso, String numeroVersaoCurso);

	@Query("from Disciplina d where d.versaoCurso = ?1")
	List<Disciplina> findAllEmVersaoCurso(VersaoCurso versaoCurso);

	@Query("SELECT b.disciplinasEquivalentes from TabelaEquivalencias t JOIN t.blocosEquivalencia b JOIN b.disciplinasOriginais d"
			+ " where d.codigo = ?1")
	List<Disciplina> findDisciplinasEquivalentesByCodigo(String codigoDisciplina);
	
	@Query("SELECT b.disciplinasOriginais from TabelaEquivalencias t JOIN t.blocosEquivalencia b JOIN b.disciplinasEquivalentes d"
			+ " where d.codigo = ?1")
	List<Disciplina> findDisciplinasOriginaisByCodigo(String codigoDisciplina);
}
