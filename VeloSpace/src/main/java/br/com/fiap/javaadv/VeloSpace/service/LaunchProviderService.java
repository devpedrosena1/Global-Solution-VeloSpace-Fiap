package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.Rocket;

import java.util.List;

public interface LaunchProviderService {

    LaunchProvider findMe();
    LaunchProvider findById(Long id);
    List<PayloadHandler> findEmployeesByLaunchProviderId(Long id);
    List<Payload> findPackagesByLaunchProviderId(Long id);
    List<Rocket> findRocketsByLaunchProviderId(Long id);
    LaunchProvider create(LaunchProvider launchProvider);
    LaunchProvider updateById(Long id, LaunchProvider launchProvider);
    LaunchProvider patchById(Long id, LaunchProvider launchProvider);
    LaunchProvider patchPasswordById(Long id, LaunchProvider launchProvider);
    void deleteById(Long id);

}
