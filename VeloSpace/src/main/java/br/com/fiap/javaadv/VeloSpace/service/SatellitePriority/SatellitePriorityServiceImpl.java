package br.com.fiap.javaadv.VeloSpace.service.SatellitePriority;

import br.com.fiap.javaadv.VeloSpace.model.SatellitePriority;
import br.com.fiap.javaadv.VeloSpace.model.repository.SatellitePriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SatellitePriorityServiceImpl implements SatellitePriorityService<SatellitePriority, Long> {

    private final SatellitePriorityRepository satellitePriorityRepository;

    @Override
    public List<SatellitePriority> findAll() {
        return satellitePriorityRepository.findAll();
    }

}
