package utp.edu.pe.appchanchitajsf.Beans;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Util.ControlFiles;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean
@RequestScoped
public class subirArchivo implements Serializable {

    private String ruta;

    // Getter y Setter para `ruta`
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    // Método para manejar la carga de archivos
    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile() != null && event.getFile().getSize() > 0) {
            try {
                InputStream input = event.getFile().getInputStream();
                String fileName = event.getFile().getFileName();
                this.ruta = saveFile(input, fileName);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Archivo cargado exitosamente."));
            } catch (IOException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar el archivo."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se cargó ningún archivo o el archivo está vacío."));
        }
    }

    // Método para guardar el archivo en el servidor
    private String saveFile(InputStream input, String fileName) throws IOException {
        LogFile.info("Método saveFile llamado.");

        // Crea el directorio si no existe
        Files.createDirectories(Paths.get(AppConfig.getRutaIMG()));

        // Genera un nuevo nombre de archivo con la fecha y hora actuales
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String newFileName = timeStamp + "_" + fileName;

        // Define la ruta completa del archivo
        String filePath = AppConfig.getRutaIMG() + File.separator + newFileName;

        // Guarda el archivo en el directorio especificado
        try (FileOutputStream output = new FileOutputStream(new File(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        return filePath;
    }

}


