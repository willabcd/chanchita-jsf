package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Negocio.FondoDAO;
import utp.edu.pe.appchanchitajsf.Negocio.TransaccionDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ReferencedBean;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ReferencedBean
public class editarListaFondo {
    private Fondo fondo;
    private List<Fondo> listaFondos ;
    public editarListaFondo() throws SQLException, NamingException, IOException {
        this.listaFondos=new ArrayList<>();
        this.fondo=new Fondo();
        this.listaFondos=FondoDAO.ListarFondo();
    }

    public List<Fondo> getListaFondos() {
        return listaFondos;
    }

    public void setListaFondos(List<Fondo> listaFondos) {
        this.listaFondos = listaFondos;
    }

    public Fondo getFondo() {
        return fondo;
    }

    public void setFondo(Fondo fondo) {
        this.fondo = fondo;
    }

    public String probarconexion(Fondo fondoh) throws SQLException, NamingException, IOException {
        FondoDAO.ActualizarFondo(fondoh);
        return null;
    }
    public String BorrarF(Fondo fondo) throws SQLException, NamingException, IOException {
        FondoDAO.BorrarFondo(fondo);
        return null;
    }

}
