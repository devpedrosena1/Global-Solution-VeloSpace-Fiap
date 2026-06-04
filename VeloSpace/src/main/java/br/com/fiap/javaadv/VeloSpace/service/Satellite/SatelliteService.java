package br.com.fiap.javaadv.VeloSpace.service.Satellite;

import org.springframework.data.domain.Page;

import br.com.fiap.javaadv.VeloSpace.infrastructure.enums.SatelliteSortField;
import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;

public interface SatelliteService<T, ID> {

    T findByIdOrThrow(ID id);

    T findById(ID id, JwtUserData authUser);

    Page<T> findAllByLaunchProviderId(
            ID id, int page, int items, SatelliteSortField sortBy, String direction, JwtUserData authUser);

    T create(T o, JwtUserData authUser);

    T updateById(ID id, T o, JwtUserData authUser);

    void deleteById(ID id, JwtUserData authUser);

}
