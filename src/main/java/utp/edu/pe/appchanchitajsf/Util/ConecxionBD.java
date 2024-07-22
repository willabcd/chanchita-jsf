package utp.edu.pe.appchanchitajsf.Util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConecxionBD {
public enum TipoDA {DATASOURCE,CLASS_FORNAME};

public static Connection conexion(TipoDA tipoDA,String cnx) throws SQLException, NamingException{
    Connection con = null;
    try{
        if (tipoDA==tipoDA.DATASOURCE){

            con= ((DataSource) InitialContext.doLookup(cnx)).getConnection();;
        }else{
            Class.forName("org.mariadb.jdbc.Driver");
            con= DriverManager.getConnection(cnx);
        }

    }catch (NamingException e){
        throw new NamingException(e.getExplanation());
    }catch (SQLException e){
        throw new SQLException(e);
    }catch (ClassNotFoundException e){
        throw new RuntimeException(e);
    }
    return con;
}

}
