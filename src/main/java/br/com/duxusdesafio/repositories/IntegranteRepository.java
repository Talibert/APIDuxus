package br.com.duxusdesafio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.duxusdesafio.model.Integrante;

@Repository
// Interface para utilizar a chave primária
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {
    
    // Variação para encontrar o integrante pelo nome se for desejado
    Optional<Integrante> findByNome(String nome);
}
