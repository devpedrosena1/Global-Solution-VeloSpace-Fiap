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
@Table(name = "shipper")
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipper_id")
    private @Getter @Setter Long shipperId;

    @Column(name = "name", nullable = false, length = 50)
    private @Getter @Setter String name;

    @Column(name = "document_shipper", nullable = false, unique = true, length = 15)
    private @Getter @Setter String documentShipper;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private @Getter @Setter String email;

    @Column(name = "phone", length = 15)
    private @Getter @Setter Long phone;

    @Column(name = "password_hash", nullable = false, length = 255)
    private @Getter @Setter String passwordHash;

    @Column(name = "type", nullable = false, length = 2)
    private @Getter @Setter String type;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shipper shipper = (Shipper) o;
        return Objects.equals(shipperId, shipper.shipperId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shipperId);
    }
}
