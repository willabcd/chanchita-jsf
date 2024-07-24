package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Transaccion;
import utp.edu.pe.appchanchitajsf.Negocio.TransaccionDAO;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class editarTransaccion {
    private Transaccion trans;
    private List<Transaccion> list;

    public editarTransaccion() throws SQLException, NamingException, IOException {
        this.trans = new Transaccion();
        this.list = new ArrayList<>();
        this.list= TransaccionDAO.listaT();
    }

    public Transaccion getTrans() {
        return trans;
    }

    public void setTrans(Transaccion trans) {
        this.trans = trans;
    }

    public List<Transaccion> getList() {
        return list;
    }

    public void setList(List<Transaccion> list) {
        this.list = list;
    }

    public String ActualizarTransaccion(Transaccion transaccion) throws IOException, SQLException, NamingException {
        LogFile.info("llamo al metodo ActualizarTransaccion");
        TransaccionDAO.actualizarTran(transaccion);
        return null;
    }
}
