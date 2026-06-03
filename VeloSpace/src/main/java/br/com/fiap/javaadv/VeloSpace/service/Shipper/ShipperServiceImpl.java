package br.com.fiap.javaadv.VeloSpace.service.Shipper;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService<Shipper, Long> {

    private final ShipperRepository shipperRepository;

    private final UserValidationService userValidationService;

    private final PasswordEncoder passwordEncoder;

    private void validateShipperOwner(JwtUserData authUser, Shipper shipper) {
        if (!Objects.equals(authUser.userId(), shipper.getShipperId())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar este remetente.");
        }
    }

    private void validateDocument(String type, String document) {
        if ("PF".equals(type)) {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.initialize(null);

            if (!cpfValidator.isValid(document, null)) {
                throw new FieldValidationException(
                        "shipperDocument",
                        "CPF inválido.");
            }

            return;
        }

        if ("PJ".equals(type)) {
            CNPJValidator cnpjValidator = new CNPJValidator();
            cnpjValidator.initialize(null);

            if (!cnpjValidator.isValid(document, null)) {
                throw new FieldValidationException(
                        "shipperDocument",
                        "CNPJ inválido.");
            }

            return;
        }

        throw new FieldValidationException(
                "type",
                "Tipo de remetente inválido.");
    }

    private void validateUniqueDocument(String document) {
        shipperRepository.findByShipperDocument(document)
                .ifPresent(other -> {
                    throw new FieldValidationException(
                            "shipperDocument",
                            "Este documento já está em uso.");
                });
    }

    @Override
    public Shipper findByIdOrThrow(Long id) {
        return shipperRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Expedidor não encontrado."));
    }

    @Override
    public List<Shipper> findAll() {
        return shipperRepository.findAll();
    }

    @Override
    public Shipper findById(Long id, JwtUserData authUser) {
        Shipper shipper = findByIdOrThrow(id);
        validateShipperOwner(authUser, shipper);
        return shipper;
    }

    @Override
    public Shipper create(Shipper shipper) {
        validateDocument(
                shipper.getType(),
                shipper.getShipperDocument());

        validateUniqueDocument(shipper.getShipperDocument());

        userValidationService.validUniqueEmail(shipper.getEmail());

        shipper.setHashedPassword(
                passwordEncoder.encode(shipper.getHashedPassword()));

        return shipperRepository.save(shipper);
    }

    @Override
    public Shipper updateById(Long id, Shipper shipper, JwtUserData authUser) {
        Shipper existing = findByIdOrThrow(id);

        validateShipperOwner(authUser, existing);

        if (!passwordEncoder.matches(
                shipper.getHashedPassword(),
                existing.getHashedPassword())) {

            throw new FieldValidationException(
                    "password",
                    "Senha atual incorreta.");
        }

        validateDocument(
                shipper.getType(),
                shipper.getShipperDocument());

        if (!shipper.getShipperDocument().equals(existing.getShipperDocument())) {
            validateUniqueDocument(shipper.getShipperDocument());
        }

        if (!shipper.getEmail().equals(existing.getEmail())) {
            userValidationService.validUniqueEmail(shipper.getEmail());
        }

        existing.setName(shipper.getName());
        existing.setShipperDocument(shipper.getShipperDocument());
        existing.setEmail(shipper.getEmail());
        existing.setPhone(shipper.getPhone());
        existing.setType(shipper.getType());

        return shipperRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        Shipper shipper = findByIdOrThrow(id);
        validateShipperOwner(authUser, shipper);

        shipperRepository.delete(shipper);
    }

}
