package utp.edu.pe.appchanchitajsf.Service;

import java.util.ResourceBundle;

public class AppConfig {
    static ResourceBundle proporty = ResourceBundle.getBundle("app");
    public static String getDatasource(){
        return proporty.getString("datasource");
    }
    public static String getErrorLogFile(){
        return proporty.getString("errorLog");
    }
    public static String getRutaIMG(){
        return proporty.getString("rutaimg");
    }
}
