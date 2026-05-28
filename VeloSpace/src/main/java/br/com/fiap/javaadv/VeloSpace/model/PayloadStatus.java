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
@Table(name = "payload_status")
public class PayloadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_status_id")
    private @Getter @Setter Long  payloadStatusId;

    @Column(name = "description", nullable = false)
    private @Getter @Setter String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PayloadStatus that = (PayloadStatus) o;
        return Objects.equals(payloadStatusId, that.payloadStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadStatusId);
    }
}
