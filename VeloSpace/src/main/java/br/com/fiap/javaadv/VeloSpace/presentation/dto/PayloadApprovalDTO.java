package br.com.fiap.javaadv.VeloSpace.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class PayloadApprovalDTO {

    private @Getter @Setter String status;
    private @Getter @Setter String justification;

}
