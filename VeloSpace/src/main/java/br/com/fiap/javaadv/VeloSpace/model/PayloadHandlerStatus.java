package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "vs_payload_handler_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadHandlerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_handler_status_id")
    private @Getter @Setter Long payloadHandlerStatusId;

    @Column(name = "code", nullable = false, length = 55)
    private @Getter @Setter String code;

    @Column(name = "description", nullable = false, length = 55)
    private @Getter @Setter String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PayloadHandlerStatus that = (PayloadHandlerStatus) o;
        return Objects.equals(payloadHandlerStatusId, that.payloadHandlerStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadHandlerStatusId);
    }

}
