package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

@ManagedBean
@RequestScoped
public class Login implements Serializable {
    private Persona user = new Persona();

    public Login() {

    }

    public Persona getUser() {
        return user;
    }

    public void setUser(Persona user) {
        this.user = user;
    }

    public String VerificarCredenciales() throws SQLException,
            NamingException, IOException {
        FacesContext faceContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faceContext.getExternalContext();
        LogFile.info("vereficando credenciales");
        if (Auth.isValidar(user.getCorreo(), user.getPassword())) {
            externalContext.getSessionMap().put("sessionId", user.getCorreo());
            LogFile.info("inicio session el usuario"+ externalContext.getSessionMap().get("sessionId"));
            return "entrar";
        } else {
            externalContext.invalidateSession();
            LogFile.error("credenciales erroneas");
            return "error";
        }
    }
    public Persona UsurEnSession() throws SQLException, NamingException, IOException {
        return Auth.UsuarioActivo();
    }
    public String cerrarSesion() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        externalContext.invalidateSession();


        return "login?faces-redirect=true"; // Reemplaza con la URL de tu página de inicio o login
    }
    public String redirecionLogin(){
        return "login?faces-redirect=true";
    }
}
