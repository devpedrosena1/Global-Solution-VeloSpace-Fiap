package br.com.fiap.javaadv.VeloSpace.service.PayloadPriority;

import br.com.fiap.javaadv.VeloSpace.model.PayloadPriority;
import br.com.fiap.javaadv.VeloSpace.model.repository.PayloadPriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayloadPriorityServiceImpl implements PayloadPriorityService<PayloadPriority, Long> {

    private final PayloadPriorityRepository payloadPriorityRepository;

    @Override
    public List<PayloadPriority> findAll() {
        return payloadPriorityRepository.findAll();
    }

}
