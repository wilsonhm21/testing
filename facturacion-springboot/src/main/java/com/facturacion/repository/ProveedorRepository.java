package com.facturacion.repository;

import com.facturacion.entity.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Integer> {

    @Query(value = "SELECT count(ruc) as ruc FROM facturacion.proveedor where ruc = :ruc", nativeQuery = true)
    public String verificarSiExiteProveedor(@Param("ruc") String ruc);
}
