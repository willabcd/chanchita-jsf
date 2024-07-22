package utp.edu.pe.appchanchitajsf.Util;

import utp.edu.pe.appchanchitajsf.Service.AppConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFile {
    public enum Level {INFO, WARN, ERROR};

    public static String log(String msg, Level level) throws IOException {
        String filename = AppConfig.getErrorLogFile();
        // FORMATO: FECHA HORA - LEVEL - MENSAJE
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String tiempo = ldt.format(fmt);

        // APlicando el formato para el evento
        //                   tiempo - level - mensaje
        String evento_fmt = "%s - %s - %s\r\n";
        String evento = String.format(evento_fmt, tiempo, level, msg);

        // Guardamos el log en archivo
        TextUTP.append(evento, filename); // <---- ver los logs en la ruta filename
        // Mostramos el log en la consola
        System.out.println(evento);

        return evento;
    }

    public static String info(String msg) throws IOException {
        return log(msg, Level.INFO);
    }

    public static String error(String msg) throws IOException {
        return log(msg, Level.ERROR);
    }

    public static String warn(String msg) throws IOException {
        return log(msg, Level.WARN);
    }

}
