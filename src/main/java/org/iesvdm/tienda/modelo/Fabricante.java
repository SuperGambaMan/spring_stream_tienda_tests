package org.iesvdm.tienda.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Fabricante {

    @Id
    public int codigo;

    private String nombre;

    @OneToMany(mappedBy = "fabricante", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Producto> productos;

}
