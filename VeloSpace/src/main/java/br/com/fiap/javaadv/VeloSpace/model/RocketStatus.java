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
@Table(name = "rocket_status")
public class RocketStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rocket_status_id")
    private @Getter @Setter Long rocketStatusId;

    @Column(name = "description", nullable = false)
    private @Getter @Setter String description;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RocketStatus that = (RocketStatus) o;
        return Objects.equals(rocketStatusId, that.rocketStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rocketStatusId);
    }
}
