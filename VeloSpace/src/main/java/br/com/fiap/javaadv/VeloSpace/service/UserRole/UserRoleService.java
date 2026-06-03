package br.com.fiap.javaadv.VeloSpace.service.UserRole;

public interface UserRoleService<T, ID> {

    T findByCode(String code);

    T getRequiredByCode(String code);

}
