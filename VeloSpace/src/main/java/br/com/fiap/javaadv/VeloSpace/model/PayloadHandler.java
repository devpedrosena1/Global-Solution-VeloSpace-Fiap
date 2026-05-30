package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "vs_payload_handler")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadHandler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_handler_id")
    private @Getter @Setter Long payloadHandlerId;

    @ManyToOne
    @JoinColumn(name = "launch_provider_id", nullable = false)
    private @Getter @Setter LaunchProvider launchProvider;

    @Column(name = "cpf", nullable = false, unique = true)
    private @Getter @Setter Long cpf;

    @Column(name = "name", nullable = false, length = 255)
    private @Getter @Setter String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Column(name = "phone")
    private @Getter @Setter Long phone;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private @Getter @Setter String hashedPassword;

    @ManyToOne
    @JoinColumn(name = "payload_handler_status_id", nullable = false)
    private @Getter @Setter PayloadHandlerStatus payloadHandlerStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PayloadHandler that = (PayloadHandler) o;
        return Objects.equals(payloadHandlerId, that.payloadHandlerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadHandlerId);
    }

}
