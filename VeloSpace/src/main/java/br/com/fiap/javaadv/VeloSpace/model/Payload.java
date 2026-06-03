package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "vs_payload")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_id")
    private @Getter @Setter Long payloadId;

    @ManyToOne
    @JoinColumn(name = "shipper_id", nullable = false)
    private @Getter @Setter Shipper shipper;

    @ManyToOne
    @JoinColumn(name = "launch_provider_id", nullable = false)
    private @Getter @Setter LaunchProvider launchProvider;

    @Column(name = "name", nullable = false, length = 55)
    private @Getter @Setter String name;

    @Column(name = "height", nullable = false)
    private @Getter @Setter Integer height;

    @Column(name = "width", nullable = false)
    private @Getter @Setter Integer width;

    @Column(name = "length", nullable = false)
    private @Getter @Setter Integer length;

    @Column(name = "weight", nullable = false)
    private @Getter @Setter Integer weight;

    @Column(name = "launch_justification", nullable = false, length = 500)
    private @Getter @Setter String launchJustification;

    @ManyToOne
    @JoinColumn(name = "payload_status_id", nullable = false)
    private @Getter @Setter PayloadStatus payloadStatus;

    @ManyToOne
    @JoinColumn(name = "payload_priority_id")
    private @Getter @Setter PayloadPriority payloadPriority;

    @Column(name = "tracking_code", length = 55)
    private @Getter @Setter String trackingCode;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Payload payload = (Payload) o;
        return Objects.equals(payloadId, payload.payloadId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payloadId);
    }

}
