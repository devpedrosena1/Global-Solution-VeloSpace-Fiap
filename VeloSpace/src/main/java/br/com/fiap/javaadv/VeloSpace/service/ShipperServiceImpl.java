package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository repository;
    private final PayloadRepository payloadRepository;

    @Override
    public LaunchProvider findMe() {
        // TODO: Podemos implementar isso depois quando tiver security (não sei como voce quer implementar ao certo)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Shipper> findAll() {
        return new ArrayList<>(
                repository.findAll()
        );
    }

    @Override
    public List<Payload> findPackagesByShipperId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Shipper not found with id: " + id);
        }
        return payloadRepository.findByShipper_ShipperId(id);

    }

    @Override
    public Optional<Shipper> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Shipper create(Shipper shipper) {
        return repository.save(shipper);
    }

    @Override
    public Shipper updateById(Long id, Shipper shipper) {
        return repository.findById(id)
                .map(existingShipper -> {
                    existingShipper.setName(shipper.getName());
                    existingShipper.setDocumentShipper(shipper.getDocumentShipper());
                    existingShipper.setEmail(shipper.getEmail());
                    existingShipper.setPhone(shipper.getPhone());
                    existingShipper.setPasswordHash(shipper.getPasswordHash());
                    existingShipper.setType(shipper.getType());
                    return repository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public Shipper patchById(Long id, Shipper shipper) {
        return repository.findById(id)
                .map(existingShipper -> {
                    if (shipper.getName() != null) existingShipper.setName(shipper.getName());
                    if (shipper.getDocumentShipper() != null) existingShipper.setDocumentShipper(shipper.getDocumentShipper());
                    if (shipper.getEmail() != null) existingShipper.setEmail(shipper.getEmail());
                    if (shipper.getPhone() != null) existingShipper.setPhone(shipper.getPhone());
                    if (shipper.getPasswordHash() != null) existingShipper.setPasswordHash(shipper.getPasswordHash());
                    if (shipper.getType() != null) existingShipper.setType(shipper.getType());
                    return repository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public Shipper patchPasswordById(Long id, Shipper shipper) {
        return repository.findById(id)
                .map(existingShipper -> {
                    if (shipper.getPasswordHash() != null) existingShipper.setPasswordHash(shipper.getPasswordHash());
                    return repository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
