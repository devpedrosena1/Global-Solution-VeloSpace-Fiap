package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "vs_payload_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayloadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_status_id")
    private @Getter @Setter Long payloadStatusId;

    @Column(name = "code", nullable = false, unique = true, length = 55)
    private @Getter @Setter String code;

    @Column(name = "description", nullable = false, unique = true, length = 55)
    private @Getter @Setter String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PayloadStatus that = (PayloadStatus) o;
        return Objects.equals(payloadStatusId, that.payloadStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadStatusId);
    }

}
