package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Negocio.UserDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
@ManagedBean
@RequestScoped
public class NewUserBeam implements Serializable {
    private Persona user;

    public NewUserBeam() {
        this.user=new Persona();
    }

    public Persona getUser() {
        return user;
    }

    public void setUser(Persona user) {
        this.user = user;
    }
    public String registrar() throws SQLException, NamingException, IOException {
        UserDAO.NewUser(this.user);
        return "login?faces-redirect=true";
    }
}
