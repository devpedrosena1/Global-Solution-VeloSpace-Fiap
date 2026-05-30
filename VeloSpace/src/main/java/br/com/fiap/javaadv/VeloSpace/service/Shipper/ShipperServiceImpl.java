package br.com.fiap.javaadv.VeloSpace.service.Shipper;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService<Shipper, Long> {

    private final ShipperRepository shipperRepository;

    private final PasswordEncoder passwordEncoder;

    private final PayloadRepository payloadRepository;

    private final UserValidationService userValidationService;

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
        shipperRepository.findByShipperDocument(shipper.getShipperDocument())
                .ifPresent(other -> {
                    throw new FieldValidationException("shipperDocument", "Este documento já está em uso.");
                });

        if(shipper.getType().equals("PF")){
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.initialize(null);
            if (!cpfValidator.isValid(shipper.getShipperDocument(), null)) {
                throw new FieldValidationException("shipperDocument", "Este documento é inválido.");
            }
        }

        if(shipper.getType().equals("PJ")){
            CNPJValidator cnpjValidator = new CNPJValidator();
            cnpjValidator.initialize(null);
            if (!cnpjValidator.isValid(shipper.getShipperDocument(), null)) {
                throw new FieldValidationException("shipperDocument", "Este documento é inválido.");
            }
        }

        userValidationService.validUniqueEmail(shipper.getEmail());
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
