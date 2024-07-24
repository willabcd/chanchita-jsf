package utp.edu.pe.appchanchitajsf.Beans;

import org.primefaces.model.file.UploadedFile;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class subirArchivo implements Serializable {

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws IOException {
        LogFile.info("se llamo al metodo upload");
        if (file != null) {
            LogFile.info("file no esta vacio");
            try (InputStream input = file.getInputStream()) {
                LogFile.info("se entro al ciclo try");
                // Save the file with a unique name or to a specific location
                File targetFile = new File(AppConfig.getRutaIMG() + file.getFileName());
                try (FileOutputStream output = new FileOutputStream(targetFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
                // Add a success message or handle accordingly
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Success",  " is uploaded."));
            } catch (IOException e) {
                LogFile.info("Error al subir el archivo: " + e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Error", "File upload failed."));
            }
        } else {
            LogFile.info("file esta vacio");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error", "No file selected."));
        }
    }
}
