package br.com.fiap.javaadv.VeloSpace.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.javaadv.VeloSpace.model.repository.LaunchProviderRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadHandlerRepository;
import br.com.fiap.javaadv.VeloSpace.model.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthConfig implements UserDetailsService {

    private final ShipperRepository shipperRepository;

    private final LaunchProviderRepository launchProviderRepository;

    private final PayloadHandlerRepository payloadHandlerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return shipperRepository.findByEmail(username)
                .map(AuthUserDetails::fromShipper)
                .or(() -> launchProviderRepository.findByEmail(username)
                        .map(AuthUserDetails::fromLaunchProvider))
                .or(() -> payloadHandlerRepository.findByEmail(username)
                        .map(AuthUserDetails::fromPayloadHandler))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}
