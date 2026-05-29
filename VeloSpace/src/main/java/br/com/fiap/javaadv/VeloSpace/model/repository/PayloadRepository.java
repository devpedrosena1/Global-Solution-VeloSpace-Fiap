package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayloadRepository extends JpaRepository<Payload, Long> {

    List<Payload> findByShipper_ShipperId(Long shipperId);
    List<Payload> findByLaunchProvider_LaunchProviderId(Long launchProviderId);

}
