package br.com.fiap.javaadv.VeloSpace.service.Shipper;

import br.com.fiap.javaadv.VeloSpace.infrastructure.security.JwtUserData;
import java.util.List;

public interface ShipperService<T, ID> {

    T findByIdOrThrow(ID id);

    List<T> findAll();

    T findById(ID id, JwtUserData authUser);

    T create(T o);

    T updateById(ID id, T o, JwtUserData authUser);

    void deleteById(ID id, JwtUserData authUser);

}
