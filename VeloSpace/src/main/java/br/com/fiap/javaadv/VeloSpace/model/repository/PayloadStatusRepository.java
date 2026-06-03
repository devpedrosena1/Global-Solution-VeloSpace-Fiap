package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.PayloadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayloadStatusRepository extends JpaRepository<PayloadStatus, Long> {

    Optional<PayloadStatus> findByCode(String code);

}
