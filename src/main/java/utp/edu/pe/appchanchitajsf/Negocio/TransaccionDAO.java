package utp.edu.pe.appchanchitajsf.Negocio;

import utp.edu.pe.appchanchitajsf.Beans.TransaccionBeam;
import utp.edu.pe.appchanchitajsf.Clases.Cuenta;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Transaccion;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Util.ConecxionBD;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO {
    public static List<Fondo> ListarF() throws IOException, SQLException, NamingException {
        String strSQL="CALL ListarFondo()";
        Connection con = ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE, AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(strSQL);
        LogFile.info("se inicio la consulta :"+strSQL);
        List<Fondo> lista =new ArrayList<>();
        while (respuesta.next()){
            Fondo fondo =new Fondo();
            fondo.setId(respuesta.getInt(1));
            fondo.setRecaudado(respuesta.getDouble(2));
            fondo.setNombre(respuesta.getString(3));

            Persona persona = new Persona();
            persona.setNombre(respuesta.getString(4));
            persona.setApellido(respuesta.getString(5));
            persona.setDni(respuesta.getInt(6));
            persona.setNotificaciones(respuesta.getBoolean(7));
            fondo.setEncargado(persona);

            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(respuesta.getString(8));
            fondo.setCuentaAsociada(cuenta);

            lista.add(fondo);
        }

        con.close();
        return lista;
    }
    public static void RegistarTransaccionBS(Transaccion transaccion)throws IOException, SQLException, NamingException{
        String strSQL="CALL IngresarTransCompleto(?, ?, ?, ?, ? ,?,?,?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, null)";
        try (Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());){
            try(PreparedStatement stmt = con.prepareStatement(strSQL)) {
                stmt.setInt(1,transaccion.getFondoAsociado().getId());
                stmt.setString(2,transaccion.getUsuarioAsociado().getNombre());
                stmt.setString(3,transaccion.getUsuarioAsociado().getApellido());
                stmt.setInt(4,transaccion.getUsuarioAsociado().getDni());
                stmt.setInt(5,transaccion.getUsuarioAsociado().isNotificaciones()  == true ? 1 : 0);
                stmt.setString(6,transaccion.getUsuarioAsociado().getCorreo());
                stmt.setString(7,transaccion.getUsuarioAsociado().getPassword());
                stmt.setInt(8,transaccion.getUsuarioAsociado().getRol().getID());
                stmt.setString(9,transaccion.getResponsable().getNombre());
                stmt.setString(10,transaccion.getResponsable().getApellido());
                stmt.setInt(11,transaccion.getResponsable().getDni());
                stmt.setInt(12,transaccion.getResponsable().isNotificaciones()  == true ? 1 : 0);
                stmt.setString(13,transaccion.getResponsable().getCorreo());
                stmt.setString(14,transaccion.getResponsable().getPassword());
                stmt.setInt(15,transaccion.getResponsable().getID());
                stmt.setDouble(16,transaccion.getMonto());
                stmt.setString(17,transaccion.getFecha());
                stmt.setString(18,transaccion.getHora());
                stmt.setBytes(19,transaccion.getEvidencia());

                LogFile.info("Executing query: " + stmt.toString());
                int ne = stmt.executeUpdate();
                con.close();
                LogFile.info("Number of affected rows: " + ne);
            }

        }catch (SQLException e){
            String msg = String.format("Exepcion Ocurrida En La Consulta : %s", e.getMessage());
            LogFile.error(msg);
        }
    }
}