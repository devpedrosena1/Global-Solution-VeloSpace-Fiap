package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "vs_launch_provider")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaunchProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "launch_provider_id")
    private @Getter @Setter Long launchProviderId;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private @Getter @Setter String cnpj;

    @Column(name = "corporate_name", nullable = false, length = 255)
    private @Getter @Setter String corporateName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Column(name = "phone")
    private @Getter @Setter Long phone;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private @Getter @Setter String hashedPassword;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        LaunchProvider that = (LaunchProvider) o;
        return Objects.equals(launchProviderId, that.launchProviderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(launchProviderId);
    }

}
