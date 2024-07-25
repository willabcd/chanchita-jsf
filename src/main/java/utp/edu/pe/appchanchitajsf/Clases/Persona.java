package utp.edu.pe.appchanchitajsf.Clases;

public class Persona {
    private int ID;
    private String nombre;
    private String apellido;
    private String dni;
    private boolean notificaciones;
    private String correo;
    private String password;
    private Rol rol;

    public Persona() {
        this.rol=new Rol();
    }

    public Persona(int ID, String nombre, String apellido, String dni, boolean notificaciones, String correo, String password, Rol rol) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.notificaciones = notificaciones;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
