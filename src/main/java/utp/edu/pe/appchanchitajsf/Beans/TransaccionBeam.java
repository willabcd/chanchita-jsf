package utp.edu.pe.appchanchitajsf.Beans;

import org.primefaces.model.file.UploadedFile;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Transaccion;
import utp.edu.pe.appchanchitajsf.Negocio.TransaccionDAO;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.ControlFiles;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class TransaccionBeam  implements Serializable {

private Transaccion transaccion;
private List<Fondo> listaF;
private int fondoaS;
private UploadedFile file;

    public TransaccionBeam() throws SQLException, NamingException, IOException {
        this.transaccion=new Transaccion();
        this.transaccion.setResponsable(Auth.UsuarioActivo());
        this.transaccion.setHora();
        this.transaccion.setFecha();
        this.listaF=new ArrayList<>();
        this.listaF=TransaccionDAO.ListarF();
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public List<Fondo> getListaF() {
        return listaF;
    }

    public void setListaF(List<Fondo> listaF) {
        this.listaF = listaF;
    }

    public int getFondoaS() {
        return fondoaS;
    }

    public void setFondoaS(int fondoaS) {
        this.fondoaS = fondoaS;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String registrarTrab() throws SQLException, NamingException, IOException {
        String ruta;
        if(this.fondoaS!=0){
            this.transaccion.setFondoAsociado(obtenerFondoPorId(fondoaS));
        }
        if (this.file!=null){
            LogFile.info("si se guardo el input file");
           ruta= ControlFiles.saveFile(file);
           this.transaccion.setEvidencia(ruta.getBytes());
        }
        TransaccionDAO.RegistarTransaccionBS(this.transaccion);
        return null;
    }
    private Fondo obtenerFondoPorId(int id) {
        for (Fondo fondo : listaF) {
            if (fondo.getId() == id) {
                return fondo;
            }
        }
        return null;
    }

}
