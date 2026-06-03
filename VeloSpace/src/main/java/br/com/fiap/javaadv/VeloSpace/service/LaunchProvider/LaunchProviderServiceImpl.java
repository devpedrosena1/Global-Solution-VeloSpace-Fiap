package br.com.fiap.javaadv.VeloSpace.service.LaunchProvider;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserValidation.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LaunchProviderServiceImpl implements LaunchProviderService<LaunchProvider, Long> {

    private final LaunchProviderRepository launchProviderRepository;

    private final UserValidationService userValidationService;

    private final PasswordEncoder passwordEncoder;

    private void validateLaunchProviderOwner(JwtUserData authUser, LaunchProvider launchProvider) {
        if (!Objects.equals(authUser.userId(), launchProvider.getLaunchProviderId())) {
            throw new ForbiddenException(
                    "Você não possui permissão para acessar esta provedora de lançamento.");
        }
    }

    @Override
    public LaunchProvider findByIdOrThrow(Long id) {
        return launchProviderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Provedora de lançamento não encontrada."));
    }

    @Override
    public List<LaunchProvider> findAll() {
        return launchProviderRepository.findAll();
    }

    @Override
    public LaunchProvider findById(Long id, JwtUserData authUser) {
        LaunchProvider launchProvider = findByIdOrThrow(id);
        validateLaunchProviderOwner(authUser, launchProvider);
        return launchProvider;
    }

    @Override
    public LaunchProvider create(LaunchProvider launchProvider) {
        launchProviderRepository.findByCnpj(launchProvider.getCnpj())
                .ifPresent(other -> {
                    throw new FieldValidationException(
                            "cnpj",
                            "Este CNPJ já está em uso.");
                });

        userValidationService.validUniqueEmail(launchProvider.getEmail());

        launchProvider.setHashedPassword(
                passwordEncoder.encode(launchProvider.getHashedPassword()));

        return launchProviderRepository.save(launchProvider);
    }

    @Override
    public LaunchProvider updateById(Long id, LaunchProvider launchProvider, JwtUserData authUser) {
        LaunchProvider existing = findByIdOrThrow(id);

        validateLaunchProviderOwner(authUser, existing);

        if (!passwordEncoder.matches(
                launchProvider.getHashedPassword(),
                existing.getHashedPassword())) {

            throw new FieldValidationException(
                    "password",
                    "Senha atual incorreta.");
        }

        if (!launchProvider.getCnpj().equals(existing.getCnpj())) {
            launchProviderRepository.findByCnpj(launchProvider.getCnpj())
                    .ifPresent(other -> {
                        throw new FieldValidationException(
                                "cnpj",
                                "Este CNPJ já está em uso.");
                    });
        }

        if (!launchProvider.getEmail().equals(existing.getEmail())) {
            userValidationService.validUniqueEmail(launchProvider.getEmail());
        }

        existing.setCorporateName(launchProvider.getCorporateName());
        existing.setCnpj(launchProvider.getCnpj());
        existing.setPhone(launchProvider.getPhone());
        existing.setEmail(launchProvider.getEmail());

        return launchProviderRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        LaunchProvider launchProvider = findByIdOrThrow(id);
        validateLaunchProviderOwner(authUser, launchProvider);
        launchProviderRepository.delete(launchProvider);
    }

}
