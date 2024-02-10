package br.com.duxusdesafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.duxusdesafio.model.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
    
}
