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
@Table(name = "payload_priority")
public class PayloadPriority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_priority_id")
    private @Getter @Setter Long payloadPriorityId;

    @Column(name = "description", nullable = false, length = 40)
    private @Getter @Setter String description;

    @Column(name = "level", nullable = false)
    private @Getter @Setter int level;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PayloadPriority that = (PayloadPriority) o;
        return Objects.equals(payloadPriorityId, that.payloadPriorityId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadPriorityId);
    }
}
