package br.com.fiap.javaadv.VeloSpace.service.UserValidation;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final ShipperRepository shipperRepository;

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final LaunchProviderRepository launchProviderRepository;

    public void validUniqueEmail(String email) {
        boolean emailAlreadyExists =
                shipperRepository.findByEmail(email).isPresent()
                        || payloadHandlerRepository.findByEmail(email).isPresent()
                        || launchProviderRepository.findByEmail(email).isPresent();

        if (emailAlreadyExists) {
            throw new FieldValidationException("email", "Este e-mail já está em uso.");
        }
    }

}
