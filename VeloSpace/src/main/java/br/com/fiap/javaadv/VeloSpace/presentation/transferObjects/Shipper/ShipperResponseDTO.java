package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.Shipper;

import br.com.fiap.javaadv.VeloSpace.model.Shipper;
import lombok.Builder;

@Builder
public record ShipperResponseDTO(
        Long shipperId,
        String type,
        String shipperDocument,
        String name,
        String email,
        Long phone) {

    public static ShipperResponseDTO from(Shipper shipper) {
        if (shipper == null)
            return null;

        return ShipperResponseDTO.builder()
                .shipperId(shipper.getShipperId())
                .type(shipper.getType())
                .shipperDocument(shipper.getShipperDocument())
                .name(shipper.getName())
                .email(shipper.getEmail())
                .phone(shipper.getPhone())
                .build();
    }

}
