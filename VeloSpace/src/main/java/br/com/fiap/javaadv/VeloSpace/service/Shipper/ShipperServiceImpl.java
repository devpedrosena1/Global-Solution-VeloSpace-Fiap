package br.com.fiap.javaadv.VeloSpace.service.Shipper;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService<Shipper, Long> {

    private final ShipperRepository shipperRepository;

    private final LaunchProviderRepository launchProviderRepository;

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final PasswordEncoder passwordEncoder;

    private final PayloadRepository payloadRepository;

    @Override
    public LaunchProvider findMe() {
        // TO-DO: Podemos implementar isso depois quando tiver security (não sei como
        // voce quer implementar ao certo)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Shipper> findAll() {
        return new ArrayList<>(
                shipperRepository.findAll());
    }

    @Override
    public List<Payload> findPackagesByShipperId(Long id) {
        if (!shipperRepository.existsById(id)) {
            throw new RuntimeException("Shipper not found with id: " + id);
        }
        return payloadRepository.findByShipper_ShipperId(id);

    }

    @Override
    public Optional<Shipper> findById(Long id) {
        return shipperRepository.findById(id);
    }

    @Override
    public Shipper create(Shipper shipper) {
        shipperRepository.findByEmail(shipper.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        payloadHandlerRepository.findByEmail(shipper.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        launchProviderRepository.findByEmail(shipper.getEmail()).ifPresent(other -> {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        });

        shipper.setHashedPassword(passwordEncoder.encode(shipper.getHashedPassword()));
        return shipperRepository.save(shipper);
    }

    @Override
    public Shipper updateById(Long id, Shipper shipper) {
        return shipperRepository.findById(id)
                .map(existingShipper -> {
                    existingShipper.setName(shipper.getName());
                    existingShipper.setShipperDocument(shipper.getShipperDocument());
                    existingShipper.setEmail(shipper.getEmail());
                    existingShipper.setPhone(shipper.getPhone());
                    existingShipper.setHashedPassword(shipper.getHashedPassword());
                    existingShipper.setType(shipper.getType());
                    return shipperRepository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public Shipper patchById(Long id, Shipper shipper) {
        return shipperRepository.findById(id)
                .map(existingShipper -> {
                    if (shipper.getName() != null)
                        existingShipper.setName(shipper.getName());
                    if (shipper.getShipperDocument() != null)
                        existingShipper.setShipperDocument(shipper.getShipperDocument());
                    if (shipper.getEmail() != null)
                        existingShipper.setEmail(shipper.getEmail());
                    if (shipper.getPhone() != null)
                        existingShipper.setPhone(shipper.getPhone());
                    if (shipper.getHashedPassword() != null)
                        existingShipper.setHashedPassword(shipper.getHashedPassword());
                    if (shipper.getType() != null)
                        existingShipper.setType(shipper.getType());
                    return shipperRepository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public Shipper patchPasswordById(Long id, Shipper shipper) {
        return shipperRepository.findById(id)
                .map(existingShipper -> {
                    if (shipper.getHashedPassword() != null)
                        existingShipper.setHashedPassword(shipper.getHashedPassword());
                    return shipperRepository.save(existingShipper);
                })
                .orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        shipperRepository.deleteById(id);
    }

}
