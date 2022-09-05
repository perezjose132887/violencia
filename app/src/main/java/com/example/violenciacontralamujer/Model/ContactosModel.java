package com.example.violenciacontralamujer.Model;

public class ContactosModel {
    private Integer id;
    private String contacto;
    private String telefono;

    public ContactosModel(){

    }

    public ContactosModel(Integer id, String contacto, String telefono) {
        this.id = id;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return id+" - "+contacto+" - " + telefono;
    }
}
