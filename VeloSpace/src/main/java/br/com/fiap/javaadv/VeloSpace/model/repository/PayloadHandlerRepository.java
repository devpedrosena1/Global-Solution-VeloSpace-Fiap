package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayloadHandlerRepository extends JpaRepository<PayloadHandler, Long> {
}
