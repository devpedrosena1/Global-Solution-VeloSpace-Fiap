package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandlerStatus;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayloadHandlerStatusRepository extends JpaRepository<PayloadHandlerStatus, Long> {

    Optional<PayloadHandlerStatus> findByCode(String code);

}
