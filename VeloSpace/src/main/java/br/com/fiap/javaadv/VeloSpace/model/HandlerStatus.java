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
@Table(name = "handler_status")
public class HandlerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "handler_status_id")
    private @Getter @Setter Long handlerStatusId;

    @Column(name = "description", nullable = false, length = 30)
    private @Getter @Setter String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HandlerStatus that = (HandlerStatus) o;
        return Objects.equals(handlerStatusId, that.handlerStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(handlerStatusId);
    }
}
