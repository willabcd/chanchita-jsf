package utp.edu.pe.appchanchitajsf.Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
@ManagedBean
@SessionScoped
public class RegistroLogin implements Serializable {
private String sessionID;

    public RegistroLogin() {

    }
    public void vereficar(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        sessionID = (String) externalContext.getSessionMap().get("sessionId");
        if (sessionID == null) {
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSessionID() {
        return sessionID;
    }




}
