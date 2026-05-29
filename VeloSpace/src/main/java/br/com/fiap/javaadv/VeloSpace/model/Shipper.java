package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "vs_shipper")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id")
    private @Getter @Setter Long shipperId;

    @Column(name = "type", nullable = false, length = 2)
    private @Getter @Setter String type;

    @Column(name = "name", nullable = false, length = 55)
    private @Getter @Setter String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Column(name = "shipper_document", nullable = false, unique = true, length = 14)
    private @Getter @Setter String shipperDocument;

    @Column(name = "phone")
    private @Getter @Setter Long phone;

    @Column(name = "hashed_password", nullable = false, length = 255)
    private @Getter @Setter String hashedPassword;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Shipper shipper = (Shipper) o;
        return Objects.equals(shipperId, shipper.shipperId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shipperId);
    }

}
