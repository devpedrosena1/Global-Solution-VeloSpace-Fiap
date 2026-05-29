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
@Table(name = "payload")
public class Payload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_id")
    private @Getter @Setter Long payloadId;

    @Column(name = "height", nullable = false)
    private @Getter @Setter int height;

    @Column(name = "width", nullable = false)
    private @Getter @Setter int width;

    @Column(name = "depth", nullable = false)
    private @Getter @Setter int depth;

    @Column(name = "weight", nullable = false)
    private @Getter @Setter int weight;

    @Column(name = "tracking_code", length = 55)
    private @Getter @Setter String trackingCode;

    @ManyToOne
    @JoinColumn(name = "payload_status_id", nullable = false)
    private @Getter @Setter PayloadStatus payloadStatus;

    @ManyToOne
    @JoinColumn(name = "shipper_id", nullable = false)
    private @Getter @Setter Shipper shipper;

    @Column(name = "justification", length = 500)
    private @Getter @Setter String justification;

    @ManyToOne
    @JoinColumn(name = "payload_priority_id", nullable = false)
    private @Getter @Setter PayloadPriority payloadPriority;

    @ManyToOne
    @JoinColumn(name = "launch_provider_id", nullable = false)
    private @Getter @Setter LaunchProvider launchProvider;

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
