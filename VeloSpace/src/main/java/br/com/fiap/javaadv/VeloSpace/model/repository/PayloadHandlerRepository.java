package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayloadHandlerRepository extends JpaRepository<PayloadHandler, Long> {

    List<PayloadHandler> findByLaunchProvider_LaunchProviderId(Long launchProviderId);

}
