package utp.edu.pe.appchanchitajsf.Service;

import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Rol;
import utp.edu.pe.appchanchitajsf.Util.ConecxionBD;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
    public static String ID_persona;

    public static Boolean isValidar(String correo, String contraseña) throws SQLException, NamingException, IOException {
        String contraseñaCifrada = md5(contraseña);
        String sql=String.format("CALL Validar('%s','%s')",correo,contraseñaCifrada);
        Connection con = ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(sql);
        LogFile.info("inicio validacion");
        boolean isValid = false;
        if (respuesta.next()){
            ID_persona=respuesta.getNString(1);
            isValid=true;
            LogFile.info("validacion correcta");
        }
        con.close();;

        return  isValid;
    }
    public static Persona UsuarioActivo() throws IOException, SQLException, NamingException {
        Persona UserActivo=new Persona();
        Rol rol=new Rol();
        String Sql=String.format("CALL EncontrarUserID('%s')",Auth.ID_persona);
        Connection con =ConecxionBD.conexion(ConecxionBD.TipoDA.DATASOURCE,AppConfig.getDatasource());
        ResultSet respuesta=con.createStatement().executeQuery(Sql);
        if (respuesta.next()){
            UserActivo.setID(respuesta.getInt(1));
            UserActivo.setNombre(respuesta.getString(2));
            UserActivo.setApellido(respuesta.getString(3));
            UserActivo.setDni(respuesta.getString(4));
            UserActivo.setNotificaciones(respuesta.getBoolean(5));
            UserActivo.setCorreo(respuesta.getString(6));
            UserActivo.setPassword(respuesta.getString(7));

            rol.setID(respuesta.getInt(8));
            UserActivo.setRol(rol);
        }
        con.close();
        return UserActivo;

    }
    public static String md5(String data) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            LogFile.error(e.getMessage());
            return data;
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
