package br.com.echobeacon.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.echobeacon.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long> {
}
