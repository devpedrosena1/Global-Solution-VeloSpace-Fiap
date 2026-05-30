package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vs_screening")
@NoArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private @Getter @Setter Long screeningId;

    @ManyToOne
    @JoinColumn(name = "payload_id", nullable = false)
    private @Getter @Setter Payload payload;

    @ManyToOne
    @JoinColumn(name = "payload_handler_id", nullable = false)
    private @Getter @Setter PayloadHandler payloadHandler;

    @Column(name = "measured_height", nullable = false)
    private @Getter @Setter int measuredHeight;

    @Column(name = "measured_width", nullable = false)
    private @Getter @Setter int measuredWidth;

    @Column(name = "measure_length", nullable = false)
    private @Getter @Setter int measureLength;

    @Column(name = "measured_weight", nullable = false)
    private @Getter @Setter int measuredWeight;

    @Column(name = "inspection_date", nullable = false)
    private @Getter @Setter LocalDate inspectionDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Screening screening = (Screening) o;
        return Objects.equals(screeningId, screening.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(screeningId);
    }

}
