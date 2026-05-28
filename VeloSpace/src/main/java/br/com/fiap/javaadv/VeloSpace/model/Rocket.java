package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rocket")
public class Rocket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rocket_id")
    private @Getter @Setter Long rocketId;

    @Column(name = "name", nullable = false, length = 40)
    private @Getter @Setter String name;

    @Column(name = "capacity_height", nullable = false)
    private @Getter @Setter int capacityHeight;

    @Column(name = "capacity_width", nullable = false)
    private @Getter @Setter int capacityWidth;

    @Column(name = "capacity_depth", nullable = false)
    private @Getter @Setter int capacityDepth;

    @Column(name = "capacity_weight", nullable = false)
    private @Getter @Setter int capacityWeight;

    @Column(name = "launch_date", nullable = false)
    private @Getter @Setter LocalDate launchDate;

    @ManyToOne
    @JoinColumn(name = "rocket_status_id", nullable = false)
    private @Getter @Setter RocketStatus rocketStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(rocketId, rocket.rocketId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rocketId);
    }
}
