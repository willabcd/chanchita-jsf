package utp.edu.pe.appchanchitajsf.Negocio;

import utp.edu.pe.appchanchitajsf.Beans.TransaccionBeam;
import utp.edu.pe.appchanchitajsf.Clases.Cuenta;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Transaccion;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.ConecxionBD;
import utp.edu.pe.appchanchitajsf.Util.LogFile;
import utp.edu.pe.appchanchitajsf.Util.extra;

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
            persona.setDni(respuesta.getString(6));
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
    public static void RegistrarTransaccionBS(Transaccion transaccion) throws IOException, SQLException, NamingException {
        // Define the stored procedure call
        String strSQL = "CALL IngresarTransCompleto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        LogFile.info("Llamando al método para registrar transacción en el DAO");

        try (Connection con = ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE, AppConfig.getDatasource());
             PreparedStatement stmt = con.prepareStatement(strSQL)) {

            // Set parameters for the stored procedure
            stmt.setInt(1, transaccion.getFondoAsociado().getId());
            stmt.setString(2, transaccion.getUsuarioAsociado().getNombre());
            stmt.setString(3, transaccion.getUsuarioAsociado().getApellido());
            int dniCliente = extra.convertStringToInt(transaccion.getUsuarioAsociado().getDni());
            stmt.setInt(4, dniCliente);
            stmt.setInt(5, transaccion.getUsuarioAsociado().isNotificaciones() ? 1 : 0);
            stmt.setString(6, transaccion.getUsuarioAsociado().getCorreo());
            stmt.setString(7, transaccion.getUsuarioAsociado().getPassword());
            stmt.setInt(8, transaccion.getUsuarioAsociado().getRol().getID());
            stmt.setString(9, transaccion.getResponsable().getNombre());
            stmt.setString(10, transaccion.getResponsable().getApellido());
            int dniResponsable = extra.convertStringToInt(transaccion.getResponsable().getDni());
            stmt.setInt(11, dniResponsable);
            stmt.setInt(12, transaccion.getResponsable().isNotificaciones() ? 1 : 0);
            stmt.setString(13, transaccion.getResponsable().getCorreo());
            stmt.setString(14, transaccion.getResponsable().getPassword());
            stmt.setInt(15, transaccion.getResponsable().getRol().getID()); // Asegúrate de que el ID es correcto aquí
            stmt.setDouble(16, transaccion.getMonto());
            stmt.setString(17, transaccion.getFecha()); // Usa Date.valueOf para el formato DATE
            stmt.setString(18, transaccion.getHora());  // Usa Time.valueOf para el formato TIME

            LogFile.info("Ejecutando consulta: " + stmt.toString());
            int ne = stmt.executeUpdate();
            LogFile.info("Número de filas afectadas: " + ne);

        } catch (SQLException e) {
            String msg = String.format("Excepción ocurrida en la consulta: %s", e.getMessage());
            LogFile.error(msg);
        }
    }

    public static List<Transaccion> listaT() throws SQLException, NamingException, IOException {
        String strSQL = String.format("CALL ListarTransacciones(%s)", Auth.ID_persona);
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(strSQL);
        LogFile.info("se inicio la consulta :"+strSQL);
        List<Transaccion> listaT =new ArrayList<>();
        while (respuesta.next()){
            Transaccion tran  =new Transaccion();
            tran.setId_transaccion(respuesta.getInt(1));
            tran.setMonto(respuesta.getDouble(2));

            Persona persona = new Persona();
            persona.setNombre(respuesta.getString(3));
            persona.setApellido(respuesta.getString(4));
            persona.setDni(respuesta.getString(5));

            tran.setUsuarioAsociado(persona);

            listaT.add(tran);
        }
        con.close();
        return listaT;
    }

    public static void actualizarTran(Transaccion transaccion) throws IOException, SQLException, NamingException {
        String strSQL=String.format("CALL ActualizarTransaccion(%s,'%s', '%s', %s, %s)"
                ,transaccion.getId_transaccion(),transaccion.getUsuarioAsociado().getNombre(),
                transaccion.getUsuarioAsociado().getApellido(),transaccion.getUsuarioAsociado().getDni(),
                transaccion.getMonto());
        LogFile.info("se inicio el metodo actualizarTran");
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        con.createStatement().executeQuery(strSQL);
        con.close();
    }
    public static void BorrarTran(Transaccion transaccion) throws IOException, SQLException, NamingException {
        String strSQL=String.format("call BorrarTransaccion(%s)",transaccion.getId_transaccion());
        LogFile.info("se inicio el metodo actualizarTran");
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        con.createStatement().executeQuery(strSQL);
        con.close();
    }
}
