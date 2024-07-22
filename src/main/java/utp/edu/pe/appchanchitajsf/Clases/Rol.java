package utp.edu.pe.appchanchitajsf.Clases;

public class Rol {
    private int ID;
    private String Nombre;
    public Rol() {
    }

    public Rol(int ID, String nombre) {
        this.ID = ID;
        Nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
