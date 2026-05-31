package br.com.fiap.javaadv.VeloSpace.presentation.transferObjects.PayloadHandler;

import jakarta.validation.constraints.NotNull;

public record ApprovalPayloadHandlerDTO(@NotNull Boolean approval) {
}
