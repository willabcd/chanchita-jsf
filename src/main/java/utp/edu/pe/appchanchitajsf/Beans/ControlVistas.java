package utp.edu.pe.appchanchitajsf.Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class ControlVistas implements Serializable {
    private String vistaActual = "/inicio.xhtml";

    public String getVistaActual() {
        return vistaActual;
    }

    public void setVistaActual(String vistaActual) {
        this.vistaActual = vistaActual;
    }

    public void showFondo() {
        vistaActual = "/WEB-INF/targer/Fondo/fondo.xhtml";
    }

    public void showInicio() {
        vistaActual = "inicio.xhtml";
    }

    public void showTranssaccion() {
        vistaActual = "prueba.xhtml";
    }

    public void showRegistrarTransaciones() {
        vistaActual = "/WEB-INF/targer/Transaccion/Registrar.xhtml";
    }

    public void showListaFondos() {
        vistaActual = "/WEB-INF/targer/Fondo/Listar.xhtml";
    }
}
