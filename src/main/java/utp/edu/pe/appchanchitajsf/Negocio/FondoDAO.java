package utp.edu.pe.appchanchitajsf.Negocio;

import utp.edu.pe.appchanchitajsf.Beans.Fondos;
import utp.edu.pe.appchanchitajsf.Clases.Cuenta;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.ConecxionBD;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FondoDAO {
    public static void NewFondo(Fondos fondo) throws IOException, SQLException, NamingException {
        String strSQL = "CALL IngresarFondoCompletor(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());) {
            try (PreparedStatement stmt = con.prepareStatement(strSQL)) {
                stmt.setString(1, fondo.getChanchita().getCuentaAsociada().getNumeroCuenta());
                stmt.setString(2, fondo.getChanchita().getNombre());
                stmt.setString(3, fondo.getChanchita().getEncargado().getNombre());
                stmt.setString(4, fondo.getChanchita().getEncargado().getApellido());
                stmt.setInt(5, fondo.getChanchita().getEncargado().getDni());
                stmt.setInt(6, fondo.getChanchita().getEncargado().isNotificaciones() == true ? 1 : 0);
                stmt.setString(7, fondo.getChanchita().getEncargado().getCorreo());
                stmt.setString(8, fondo.getChanchita().getEncargado().getPassword());
                stmt.setInt(9,fondo.getChanchita().getEncargado().getRol().getID());

                LogFile.info("Executing query: " + stmt.toString());
                int ne = stmt.executeUpdate();
                con.close();
                LogFile.info("Number of affected rows: " + ne);
            }
        } catch (SQLException e) {
            String msg = String.format("Exepcion Ocurrida En La Consulta : %s", e.getMessage());
            LogFile.error(msg);

        }
    }
    public static List<Fondo> ListarFondo() throws IOException, SQLException, NamingException{
        String strSQL=String.format("CALL ListarFondoUser('%s');", Auth.ID_persona);
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(strSQL);
        LogFile.info("se inicio la consulta :"+strSQL);
        List<Fondo> lista =new ArrayList<>();
        while (respuesta.next()){
            Fondo fondo =new Fondo();
            fondo.setId(respuesta.getInt(1));
            fondo.setNombre(respuesta.getString(2));

            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(respuesta.getString(3));
            fondo.setCuentaAsociada(cuenta);

            fondo.setRecaudado(respuesta.getDouble(4));

            lista.add(fondo);
        }

        con.close();
        return lista;
    }

    public static void ActualizarFondo(Fondo fondo) throws IOException, SQLException, NamingException {
        String callStatement =String.format("CALL ActualizarFondoUser(%s, '%s', %s)",fondo.getId(),fondo.getNombre(),fondo.getRecaudado()) ;
        LogFile.info("se inicio el metodo ActualizarFondo");
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        con.createStatement().executeQuery(callStatement);
        con.close();

    }

}
