package br.cefetrj.sca.dominio.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetrj.sca.dominio.atividadecomplementar.RegistroAtividadeComplementar;

public interface RegistroAtividadeComplementarRepositorio extends JpaRepository<RegistroAtividadeComplementar, Serializable>{
	
	RegistroAtividadeComplementar findRegistroAtividadeComplementarById(Long id);

}
