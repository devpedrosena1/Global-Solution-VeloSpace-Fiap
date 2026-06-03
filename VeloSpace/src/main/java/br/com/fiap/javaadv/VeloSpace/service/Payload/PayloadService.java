package br.com.fiap.javaadv.VeloSpace.service.Payload;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;

public interface PayloadService<T, ID> {

    T findByIdOrThrow(ID id);

    T findById(ID id, JwtUserData authUser);

    T create(T o, JwtUserData authUser);

    T updateById(ID id, T o, JwtUserData authUser);

    void deleteById(ID id, JwtUserData authUser);

}
