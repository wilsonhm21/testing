package com.facturacion.service;


import com.facturacion.entity.Proveedor;
import com.facturacion.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedor() {
        return (List<Proveedor>) this.proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Integer id) {
        return this.proveedorRepository.findById(id);
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return this.proveedorRepository.save(proveedor);
    }

    public void actualizarProveedor(Proveedor proveedor) {
        this.proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Integer id) {
        this.proveedorRepository.deleteById(id);
    }

    public String verificarSiExiteProveedor(String ruc) {
        return this.proveedorRepository.verificarSiExiteProveedor(ruc);
    }
}
