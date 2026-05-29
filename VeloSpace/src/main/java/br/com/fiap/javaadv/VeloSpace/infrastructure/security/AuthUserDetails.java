package br.com.fiap.javaadv.VeloSpace.infrastructure.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fiap.javaadv.VeloSpace.model.LaunchProvider;
import br.com.fiap.javaadv.VeloSpace.model.PayloadHandler;
import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthUserDetails implements UserDetails {

    private Long userId;

    private String email;

    private String hashedPassword;

    private Role role;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AuthUserDetails fromShipper(Shipper shipper) {
        if (shipper == null)
            return null;

        return AuthUserDetails.builder()
                .userId(shipper.getShipperId())
                .email(shipper.getEmail())
                .hashedPassword(shipper.getHashedPassword())
                .role(Role.SHIPPER)
                .build();
    }

    public static AuthUserDetails fromLaunchProvider(LaunchProvider launchProvider) {
        if (launchProvider == null)
            return null;

        return AuthUserDetails.builder()
                .userId(launchProvider.getLaunchProviderId())
                .email(launchProvider.getEmail())
                .hashedPassword(launchProvider.getHashedPassword())
                .role(Role.LAUNCH_PROVIDER)
                .build();
    }

    public static AuthUserDetails fromPayloadHandler(PayloadHandler payloadHandler) {
        if (payloadHandler == null)
            return null;

        return AuthUserDetails.builder()
                .userId(payloadHandler.getPayloadHandlerId())
                .email(payloadHandler.getEmail())
                .hashedPassword(payloadHandler.getHashedPassword())
                .role(Role.PAYLOAD_HANDLER)
                .build();
    }

}
