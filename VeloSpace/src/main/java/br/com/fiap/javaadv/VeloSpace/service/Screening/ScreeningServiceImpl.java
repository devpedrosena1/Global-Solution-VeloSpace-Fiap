package br.com.fiap.javaadv.VeloSpace.service.Screening;

import br.com.fiap.javaadv.VeloSpace.model.Screening;
import br.com.fiap.javaadv.VeloSpace.model.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService<Screening, Long> {

    private final ScreeningRepository screeningRepository;

    @Override
    public Screening findById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));
    }

    @Override
    public Screening create(Screening screening) {
        return screeningRepository.save(screening);
    }

    @Override
    public Screening updateById(Long id, Screening screening) {
        return screeningRepository.findById(id)
                .map(existing -> {
                    existing.setMeasuredHeight(screening.getMeasuredHeight());
                    existing.setMeasuredWidth(screening.getMeasuredWidth());
                    existing.setMeasureLength(screening.getMeasureLength());
                    existing.setMeasuredWeight(screening.getMeasuredWeight());
                    existing.setInspectionDate(screening.getInspectionDate());
                    existing.setPayloadHandler(screening.getPayloadHandler());
                    existing.setPayload(screening.getPayload());
                    return screeningRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));
    }

    @Override
    public Screening patchById(Long id, Screening screening) {
        return screeningRepository.findById(id)
                .map(existing -> {
                    if (screening.getMeasuredHeight() != 0)
                        existing.setMeasuredHeight(screening.getMeasuredHeight());
                    if (screening.getMeasuredWidth() != 0)
                        existing.setMeasuredWidth(screening.getMeasuredWidth());
                    if (screening.getMeasureLength() != 0)
                        existing.setMeasureLength(screening.getMeasureLength());
                    if (screening.getMeasuredWeight() != 0)
                        existing.setMeasuredWeight(screening.getMeasuredWeight());
                    if (screening.getInspectionDate() != null)
                        existing.setInspectionDate(screening.getInspectionDate());
                    if (screening.getPayloadHandler() != null)
                        existing.setPayloadHandler(screening.getPayloadHandler());
                    if (screening.getPayload() != null)
                        existing.setPayload(screening.getPayload());
                    return screeningRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        screeningRepository.deleteById(id);
    }

}
