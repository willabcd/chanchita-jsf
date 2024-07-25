package utp.edu.pe.appchanchitajsf.Negocio;

import utp.edu.pe.appchanchitajsf.Clases.Cuenta;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
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

import static utp.edu.pe.appchanchitajsf.Service.Auth.md5;

public class UserDAO {
    public static void  NewUser(Persona persona)throws IOException, SQLException, NamingException{
        String strSQL="call RegistrarUser(?, ?, ?, ?, ?,?)";
        try(Connection con = ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE, AppConfig.getDatasource());) {
            try (PreparedStatement stmt = con.prepareStatement(strSQL)) {
                stmt.setString(1,persona.getNombre());
                stmt.setString(2,persona.getApellido());
                int dni = extra.convertStringToInt(persona.getDni());
                stmt.setInt(3,dni);
                stmt.setString(4, persona.getCorreo());
                stmt.setString(5,md5(persona.getPassword()));
                stmt.setInt(6, persona.isNotificaciones()== true ? 1 : 0);
                LogFile.info("Executing query: " + stmt.toString());
                int ne = stmt.executeUpdate();
                con.close();
                LogFile.info("Number of affected rows: " + ne);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Persona> Credenciales()throws IOException, SQLException, NamingException{
        String strSQL="call listarCredenciales";
        Connection con = ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(strSQL);
        LogFile.info("se inicio la consulta :"+strSQL);
        List<Persona> lista =new ArrayList<>();
        while (respuesta.next()){
           Persona correo = new Persona();
           correo.setCorreo(respuesta.getString(1));
           lista.add(correo);
        }
        con.close();
        return lista;
    }
}
