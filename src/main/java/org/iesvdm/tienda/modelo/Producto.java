package org.iesvdm.tienda.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    private int codigo;
    
    private String nombre;

    private double precio;

    @ManyToOne
    @JoinColumn(name = "codigo_fabricante", referencedColumnName = "codigo")
    private Fabricante fabricante;

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
