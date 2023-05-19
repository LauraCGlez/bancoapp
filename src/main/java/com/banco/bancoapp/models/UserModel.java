package com.banco.bancoapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @Column(name = "nif", length = 9, nullable = false)
    private String nif;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "anyo_nacimiento", length = 4)
    private String anyoNacimiento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono", length = 9)
    private int telefono;

    @Column(name = "pass", unique = true)
    private String pass;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "userModels", cascade = CascadeType.ALL)
    private Set<AccountModel> accountModels = new HashSet<>();

    //TODO GETTER AND SETTER

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnyoNacimiento() {
        return anyoNacimiento;
    }

    public void setAnyoNacimiento(String anyoNacimiento) {
        this.anyoNacimiento = anyoNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Set<AccountModel> getAccountModels() {
        return accountModels;
    }

    public void setAccountModels(Set<AccountModel> accountModels) {
        this.accountModels = accountModels;
    }
}