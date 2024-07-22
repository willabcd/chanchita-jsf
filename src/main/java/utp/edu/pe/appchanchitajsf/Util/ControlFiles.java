package utp.edu.pe.appchanchitajsf.Util;


import org.primefaces.model.file.UploadedFile;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControlFiles {
    public static String saveFile(UploadedFile file) throws IOException {
        if (file != null) {
            // Crea el directorio si no existe
            Files.createDirectories(Paths.get(AppConfig.getRutaIMG()));

            // Genera un nuevo nombre de archivo con la fecha y hora actuales
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String newFileName = timeStamp + "_" + file.getFileName();

            // Define la ruta completa del archivo
            String filePath = AppConfig.getRutaIMG() + File.separator + newFileName;

            // Guarda el archivo en el directorio especificado
            try (InputStream input = file.getInputStream();
                 FileOutputStream output = new FileOutputStream(new File(filePath))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }

            return filePath;
        }

        return null;
    }
}
