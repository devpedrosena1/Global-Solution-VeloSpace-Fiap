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
@Table(name = "payload_handler")
public class PayloadHandler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_handler_id")
    private @Getter @Setter Long payloadHandlerId;

    @Column(name = "name", nullable = false, length = 40)
    private @Getter @Setter String name;

    @Column(name = "cpf", nullable = false, unique = true)
    private @Getter @Setter Long cpf;

    @ManyToOne
    @JoinColumn(name = "handler_status_id", nullable = false)
    private @Getter @Setter HandlerStatus handlerStatus;

    @ManyToOne
    @JoinColumn(name = "launch_provider_id", nullable = false)
    private @Getter @Setter LaunchProvider launchProvider;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Column(name = "phone", nullable = false, length = 15)
    private @Getter @Setter Long phone;

    @Column(name = "password_hash", nullable = false, length = 255)
    private @Getter @Setter String passwordHash;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PayloadHandler that = (PayloadHandler) o;
        return Objects.equals(payloadHandlerId, that.payloadHandlerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadHandlerId);
    }
}
