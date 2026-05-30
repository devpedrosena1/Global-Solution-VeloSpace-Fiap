package br.com.fiap.javaadv.VeloSpace.service.LaunchProvider;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;

import java.util.List;

public interface LaunchProviderService<T, ID> {

    LaunchProvider findMe();

    LaunchProvider findById(ID id);

    List<PayloadHandler> findEmployeesByLaunchProviderId(ID id);

    List<Payload> findPackagesByLaunchProviderId(ID id);

    LaunchProvider create(T o);

    LaunchProvider updateById(ID id, T o);

    LaunchProvider patchById(ID id, T o);

    LaunchProvider patchPasswordById(ID id, T o);

    void deleteById(ID id);

}
