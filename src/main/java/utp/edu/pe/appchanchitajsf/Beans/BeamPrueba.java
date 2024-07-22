package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Negocio.FondoDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
@ManagedBean
@RequestScoped
public class BeamPrueba implements Serializable {
    private int id;
    private String Nombre;
    private double monto;

    private Fondo fondo;

    public BeamPrueba() {
        this.fondo=new Fondo();
        fondo.setId(this.id);
        fondo.setNombre(this.Nombre);
        fondo.setRecaudado(this.monto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    public void actualizarfondo() throws SQLException, NamingException, IOException {

    }
}
