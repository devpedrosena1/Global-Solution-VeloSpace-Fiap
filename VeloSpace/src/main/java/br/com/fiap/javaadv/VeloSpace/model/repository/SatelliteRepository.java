package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {

    List<Satellite> findByShipper_ShipperId(Long shipperId);

    List<Satellite> findByLaunchProvider_LaunchProviderId(Long launchProviderId);

}
