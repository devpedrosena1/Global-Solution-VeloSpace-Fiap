package br.com.fiap.javaadv.VeloSpace.service.Satellite;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;

public interface SatelliteService<T, ID> {

    T findByIdOrThrow(ID id);

    T findById(ID id, JwtUserData authUser);

    T create(T o, JwtUserData authUser);

    T updateById(ID id, T o, JwtUserData authUser);

    void deleteById(ID id, JwtUserData authUser);

}
