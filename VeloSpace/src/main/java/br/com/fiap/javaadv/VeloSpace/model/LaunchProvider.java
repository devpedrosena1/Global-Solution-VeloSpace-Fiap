package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "launch_provider")
public class LaunchProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "launch_provider_id")
    private @Getter @Setter Long launchProviderId;

    @Column(name = "corporate_name", nullable = false, length = 40)
    private @Getter @Setter String corporateName;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private @Getter @Setter String cnpj;

    @Column(name = "phone", length = 15)
    private @Getter @Setter Long phone;

    @Column(name = "password_hash", nullable = false, length = 255)
    private @Getter @Setter String passwordHash;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LaunchProvider that = (LaunchProvider) o;
        return Objects.equals(launchProviderId, that.launchProviderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(launchProviderId);
    }
}
