package br.com.fiap.javaadv.VeloSpace.service.LaunchProvider;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaunchProviderServiceImpl implements LaunchProviderService<LaunchProvider, Long> {

    private final LaunchProviderRepository launchProviderRepository;

    private final PayloadHandlerRepository payloadHandlerRepository;

    private final PayloadRepository payloadRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserValidationService userValidationService;

    @Override
    public LaunchProvider findMe() {
        // TO-DO: Podemos implementar isso depois quando tiver security
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LaunchProvider findById(Long id) {
        return launchProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public List<PayloadHandler> findEmployeesByLaunchProviderId(Long id) {
        if (!launchProviderRepository.existsById(id)) {
            throw new RuntimeException("LaunchProvider not found with id: " + id);
        }
        return payloadHandlerRepository.findByLaunchProvider_LaunchProviderId(id);
    }

    @Override
    public List<Payload> findPackagesByLaunchProviderId(Long id) {
        if (!launchProviderRepository.existsById(id)) {
            throw new RuntimeException("LaunchProvider not found with id: " + id);
        }
        return payloadRepository.findByLaunchProvider_LaunchProviderId(id);
    }

    @Override
    public LaunchProvider create(LaunchProvider launchProvider) {

        userValidationService.validUniqueEmail(launchProvider.getEmail());
        launchProvider.setHashedPassword(passwordEncoder.encode(launchProvider.getHashedPassword()));
        return launchProviderRepository.save(launchProvider);
    }

    @Override
    public LaunchProvider updateById(Long id, LaunchProvider launchProvider) {
        return launchProviderRepository.findById(id)
                .map(existing -> {
                    existing.setCorporateName(launchProvider.getCorporateName());
                    existing.setCnpj(launchProvider.getCnpj());
                    existing.setPhone(launchProvider.getPhone());
                    existing.setHashedPassword(launchProvider.getHashedPassword());
                    existing.setEmail(launchProvider.getEmail());
                    return launchProviderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public LaunchProvider patchById(Long id, LaunchProvider launchProvider) {
        return launchProviderRepository.findById(id)
                .map(existing -> {
                    if (launchProvider.getCorporateName() != null)
                        existing.setCorporateName(launchProvider.getCorporateName());
                    if (launchProvider.getCnpj() != null)
                        existing.setCnpj(launchProvider.getCnpj());
                    if (launchProvider.getPhone() != null)
                        existing.setPhone(launchProvider.getPhone());
                    if (launchProvider.getHashedPassword() != null)
                        existing.setHashedPassword(launchProvider.getHashedPassword());
                    if (launchProvider.getEmail() != null)
                        existing.setEmail(launchProvider.getEmail());
                    return launchProviderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public LaunchProvider patchPasswordById(Long id, LaunchProvider launchProvider) {
        return launchProviderRepository.findById(id)
                .map(existing -> {
                    if (launchProvider.getHashedPassword() != null)
                        existing.setHashedPassword(launchProvider.getHashedPassword());
                    return launchProviderRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        launchProviderRepository.deleteById(id);
    }

}
