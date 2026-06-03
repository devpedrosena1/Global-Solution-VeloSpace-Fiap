package br.com.fiap.javaadv.VeloSpace.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.ScreeningResult;

@Entity
@Table(name = "vs_screening")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private @Getter @Setter Integer measuredHeight;

    @Column(name = "measured_width", nullable = false)
    private @Getter @Setter Integer measuredWidth;

    @Column(name = "measured_length", nullable = false)
    private @Getter @Setter Integer measuredLength;

    @Column(name = "measured_weight", nullable = false)
    private @Getter @Setter Integer measuredWeight;

    @Column(name = "result", nullable = false, length = 55)
    private @Getter @Setter ScreeningResult result;

    @Column(name = "inspection_date", nullable = false)
    private @Getter @Setter LocalDateTime inspectionDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Screening screening = (Screening) o;
        return Objects.equals(screeningId, screening.getScreeningId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(screeningId);
    }

}
