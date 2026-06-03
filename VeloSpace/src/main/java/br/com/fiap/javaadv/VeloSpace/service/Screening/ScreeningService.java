package br.com.fiap.javaadv.VeloSpace.service.Screening;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;

public interface ScreeningService<T, ID> {

    T findById(ID id, JwtUserData authUser);

    T create(T o, JwtUserData authUser);

}
