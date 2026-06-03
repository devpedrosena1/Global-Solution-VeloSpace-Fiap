package br.com.fiap.javaadv.VeloSpace.service.LaunchProvider;

import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.FieldValidationException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.ForbiddenException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.exceptions.NotFoundException;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.UserAccount;
import br.com.fiap.javaadv.VeloSpace.model.UserRole;
import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.service.UserRole.UserRoleService;
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

    private final UserRoleService<UserRole, Long> userRoleService;

    private final PasswordEncoder passwordEncoder;

    private void validateLaunchProviderOwner(JwtUserData authUser, LaunchProvider launchProvider) {
        if (!Objects.equals(authUser.userId(), launchProvider.getUserAccount().getUserAccountId())) {
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
        UserAccount userAccount = launchProvider.getUserAccount();

        userValidationService.validUniqueEmail(userAccount.getEmail());

        launchProviderRepository.findByCnpj(launchProvider.getCnpj())
                .ifPresent(other -> {
                    throw new FieldValidationException(
                            "cnpj",
                            "Este CNPJ já está em uso.");
                });

        UserRole launchProviderRole = userRoleService.getRequiredByCode("LAUNCH_PROVIDER");
        userAccount.setUserRole(launchProviderRole);

        userAccount.setHashedPassword(passwordEncoder.encode(userAccount.getHashedPassword()));
        launchProvider.setUserAccount(userAccount);

        return launchProviderRepository.save(launchProvider);
    }

    @Override
    public LaunchProvider updateById(Long id, LaunchProvider launchProvider, JwtUserData authUser) {
        LaunchProvider existing = findByIdOrThrow(id);

        validateLaunchProviderOwner(authUser, existing);

        UserAccount userAccount = launchProvider.getUserAccount();
        UserAccount existingUserAccount = existing.getUserAccount();

        if (!passwordEncoder.matches(
                userAccount.getHashedPassword(),
                existingUserAccount.getHashedPassword())) {

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

        if (!userAccount.getEmail().equals(existingUserAccount.getEmail())) {
            userValidationService.validUniqueEmail(userAccount.getEmail());
        }

        existing.setCorporateName(launchProvider.getCorporateName());
        existing.setCnpj(launchProvider.getCnpj());

        existingUserAccount.setEmail(userAccount.getEmail());
        existingUserAccount.setPhone(userAccount.getPhone());

        return launchProviderRepository.save(existing);
    }

    @Override
    public void deleteById(Long id, JwtUserData authUser) {
        LaunchProvider launchProvider = findByIdOrThrow(id);
        validateLaunchProviderOwner(authUser, launchProvider);
        launchProviderRepository.delete(launchProvider);
    }

}
