package es.ieslavereda.android_rvgot_base.model;

import java.io.Serializable;
import java.util.Comparator;

public class Personaje implements Serializable{
    private String nombre;
    private Casa casa;

    public static final Comparator<Personaje> SORT_BY_NAME = (personaje1, personaje2) -> {
            return personaje1.nombre.compareToIgnoreCase(personaje2.nombre);
    };

    public static final  Comparator<Personaje> SORT_BY_CASA = (personaje1, personaje2) -> {
        return personaje1.getCasa().getNombre().compareToIgnoreCase(personaje2.getCasa().getNombre());
    };

    public Personaje(String nombre, Casa casa) {
        this.nombre = nombre;
        this.casa = casa;
    }

    public String getNombre() {
        return nombre;
    }

    public Casa getCasa() {
        return casa;
    }

}
