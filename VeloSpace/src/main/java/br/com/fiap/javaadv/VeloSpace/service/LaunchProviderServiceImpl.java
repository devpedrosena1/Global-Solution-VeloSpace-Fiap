package br.com.fiap.javaadv.VeloSpace.service;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.Payload;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.Rocket;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.RocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaunchProviderServiceImpl implements LaunchProviderService{

    private final LaunchProviderRepository repository;
    private final PayloadHandlerRepository payloadHandlerRepository;
    private final PayloadRepository payloadRepository;
    private final RocketRepository rocketRepository;

    @Override
    public LaunchProvider findMe() {
        // TODO: Podemos implementar isso depois quando tiver security
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LaunchProvider findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public List<PayloadHandler> findEmployeesByLaunchProviderId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("LaunchProvider not found with id: " + id);
        }
        return payloadHandlerRepository.findByLaunchProvider_LaunchProviderId(id);
    }

    @Override
    public List<Payload> findPackagesByLaunchProviderId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("LaunchProvider not found with id: " + id);
        }
        return payloadRepository.findByLaunchProvider_LaunchProviderId(id);
    }

    @Override
    public List<Rocket> findRocketsByLaunchProviderId(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("LaunchProvider not found with id: " + id);
        }
        return rocketRepository.findByLaunchProvider_LaunchProviderId(id);
    }

    @Override
    public LaunchProvider create(LaunchProvider launchProvider) {
        return repository.save(launchProvider);
    }

    @Override
    public LaunchProvider updateById(Long id, LaunchProvider launchProvider) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCorporateName(launchProvider.getCorporateName());
                    existing.setCnpj(launchProvider.getCnpj());
                    existing.setPhone(launchProvider.getPhone());
                    existing.setPasswordHash(launchProvider.getPasswordHash());
                    existing.setEmail(launchProvider.getEmail());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public LaunchProvider patchById(Long id, LaunchProvider launchProvider) {
        return repository.findById(id)
                .map(existing -> {
                    if (launchProvider.getCorporateName() != null) existing.setCorporateName(launchProvider.getCorporateName());
                    if (launchProvider.getCnpj() != null) existing.setCnpj(launchProvider.getCnpj());
                    if (launchProvider.getPhone() != null) existing.setPhone(launchProvider.getPhone());
                    if (launchProvider.getPasswordHash() != null) existing.setPasswordHash(launchProvider.getPasswordHash());
                    if (launchProvider.getEmail() != null) existing.setEmail(launchProvider.getEmail());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public LaunchProvider patchPasswordById(Long id, LaunchProvider launchProvider) {
        return repository.findById(id)
                .map(existing -> {
                    if (launchProvider.getPasswordHash() != null) existing.setPasswordHash(launchProvider.getPasswordHash());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("LaunchProvider not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
