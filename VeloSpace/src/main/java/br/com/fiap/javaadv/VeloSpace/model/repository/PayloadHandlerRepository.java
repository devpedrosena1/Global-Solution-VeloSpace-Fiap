package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayloadHandlerRepository extends JpaRepository<PayloadHandler, Long> {

    public Optional<PayloadHandler> findByEmail(String email);

    List<PayloadHandler> findByLaunchProvider_LaunchProviderId(Long launchProviderId);

}
