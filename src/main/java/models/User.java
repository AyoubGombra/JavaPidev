package models;

import java.util.Objects;

public class User {
    private int id, salaire;
    private String email, role_Useer, password, name, prename, departement;
    public User() {

    }

    public User(int id, int salaire, String email, String role_Useer, String password, String name, String prename, String departement) {
        this.id = id;
        this.salaire = salaire;
        this.email = email;
        this.role_Useer = role_Useer;
        this.password = password;
        this.name = name;
        this.prename = prename;
        this.departement = departement;
    }

    public User(int salaire, String email, String role_Useer, String password, String name, String prename, String departement) {
        this.salaire = salaire;
        this.email = email;
        this.role_Useer = role_Useer;
        this.password = password;
        this.name = name;
        this.prename = prename;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole_Useer() {
        return role_Useer;
    }

    public void setRole_Useer(String role_Useer) {
        this.role_Useer = role_Useer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", salaire=" + salaire +
                ", email='" + email + '\'' +
                ", role_Useer='" + role_Useer + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", prename='" + prename + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}
