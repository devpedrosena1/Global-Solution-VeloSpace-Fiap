package br.com.fiap.javaadv.VeloSpace.service.Shipper;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;

import java.util.List;
import java.util.Optional;

public interface ShipperService<T, ID> {

    LaunchProvider findMe();

    List<Shipper> findAll(); // a ver se vamos usar mesmo, se for tem que usar paginação

    List<Payload> findPackagesByShipperId(ID id);

    Optional<Shipper> findById(ID id);

    Shipper create(T o);

    Shipper updateById(ID id, T o);

    Shipper patchById(ID id, T o);

    Shipper patchPasswordById(ID id, T o);

    void deleteById(ID id);

}
