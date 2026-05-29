package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;

import java.util.List;
import java.util.Optional;

public interface ShipperService {

    LaunchProvider findMe();
    List<Shipper> findAll(); // a ver se vamos usar mesmo, se for tem que usar paginação
    List<Payload> findPackagesByShipperId(Long id);
    Optional<Shipper> findById(Long id);
    Shipper create(Shipper shipper);
    Shipper updateById(Long id, Shipper shipper);
    Shipper patchById(Long id, Shipper shipper);
    Shipper patchPasswordById(Long id, Shipper shipper);
    void deleteById(Long id);

}
