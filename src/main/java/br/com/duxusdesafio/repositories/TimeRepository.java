package br.com.duxusdesafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.duxusdesafio.model.Time;

@Repository
// Interface para utilizar a chave primária
public interface TimeRepository extends JpaRepository<Time, Long> {
    
}
