package ni.edu.uam.distribuidora_gueguense.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int existencia;
}