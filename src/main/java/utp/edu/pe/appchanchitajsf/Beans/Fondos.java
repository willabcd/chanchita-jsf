package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Cuenta;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Rol;
import utp.edu.pe.appchanchitajsf.Negocio.FondoDAO;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class Fondos implements Serializable {
    private Fondo chanchita;
    private Persona personafondo;

    private Cuenta cuenta;
    private List<Fondo> listaFondos;

    public Fondos() throws SQLException, NamingException, IOException {

        this.listaFondos = new ArrayList<>();

        this.cuenta = new Cuenta();

        this.personafondo = new Persona(Auth.UsuarioActivo().getID(),Auth.UsuarioActivo().getNombre(),Auth.UsuarioActivo().getApellido(),Auth.UsuarioActivo().getDni(),
                Auth.UsuarioActivo().isNotificaciones(),Auth.UsuarioActivo().getCorreo(),Auth.UsuarioActivo().getPassword(),Auth.UsuarioActivo().getRol());
        this.chanchita = new Fondo();
        chanchita.setEncargado(personafondo);
        chanchita.setCuentaAsociada(cuenta);
        this.listaFondos = FondoDAO.ListarFondo();
    }

    public Fondo getChanchita() {
        return chanchita;
    }

    public void setChanchita(Fondo chanchita) {
        this.chanchita = chanchita;
    }

    public Persona getPersonafondo() {
        return personafondo;
    }

    public void setPersonafondo(Persona personafondo) {
        this.personafondo = personafondo;
    }



    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<Fondo> getListaFondos() {
        return listaFondos;
    }

    public void setListaFondos(List<Fondo> listaFondos) {
        this.listaFondos = listaFondos;
    }



    public void RegistrarFondo() throws SQLException, NamingException, IOException {
        try {
            LogFile.info("Inicio el proceso de registro de fondo");
            FondoDAO.NewFondo(this);

            // Resetear los campos después de registrar
            this.cuenta = new Cuenta();

            this.personafondo = new Persona(Auth.UsuarioActivo().getID(),Auth.UsuarioActivo().getNombre(),Auth.UsuarioActivo().getApellido(),Auth.UsuarioActivo().getDni(),
                    Auth.UsuarioActivo().isNotificaciones(),Auth.UsuarioActivo().getCorreo(),Auth.UsuarioActivo().getPassword(),Auth.UsuarioActivo().getRol());
            this.chanchita = new Fondo();
            chanchita.setEncargado(personafondo);
            chanchita.setCuentaAsociada(cuenta);
            this.listaFondos = FondoDAO.ListarFondo();

        } catch (IOException | IllegalStateException e) {
            LogFile.error("Error en la implementación de ingresar fondos: " + e.getMessage());
        }
    }


}
