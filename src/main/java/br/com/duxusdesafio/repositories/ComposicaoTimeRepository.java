package br.com.duxusdesafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.ComposicaoTime;

@Repository
public interface ComposicaoTimeRepository extends JpaRepository<ComposicaoTime, Long> {
    
}
