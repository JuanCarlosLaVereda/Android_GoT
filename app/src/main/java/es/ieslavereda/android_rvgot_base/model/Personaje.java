package es.ieslavereda.android_rvgot_base.model;

import java.io.Serializable;

public class Personaje implements Serializable{
    private String nombre;
    private Casa casa;

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
