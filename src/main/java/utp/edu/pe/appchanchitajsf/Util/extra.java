package utp.edu.pe.appchanchitajsf.Util;

import java.sql.SQLException;

public class extra {
    public static int convertStringToInt(String str) throws SQLException {
        try {
            String cleanedStr = str.replaceAll("\\D", "");
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new SQLException("El DNI proporcionado no es un número válido: " + str, e);
        }
    }
}
