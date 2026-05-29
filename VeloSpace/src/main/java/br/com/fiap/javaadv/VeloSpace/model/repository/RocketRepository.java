package br.com.fiap.javaadv.VeloSpace.model.repository;

import br.com.fiap.javaadv.VeloSpace.model.Rocket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RocketRepository extends JpaRepository<Rocket, Long> {

    List<Rocket> findByLaunchProvider_LaunchProviderId(Long launchProviderId);

}
