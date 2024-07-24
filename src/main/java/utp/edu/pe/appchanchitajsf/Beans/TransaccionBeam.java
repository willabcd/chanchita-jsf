package utp.edu.pe.appchanchitajsf.Beans;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.primefaces.model.file.UploadedFile;
import utp.edu.pe.appchanchitajsf.Clases.Fondo;
import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Clases.Transaccion;
import utp.edu.pe.appchanchitajsf.Negocio.TransaccionDAO;
import utp.edu.pe.appchanchitajsf.Service.AppConfig;
import utp.edu.pe.appchanchitajsf.Service.Auth;
import utp.edu.pe.appchanchitajsf.Util.ControlFiles;
import utp.edu.pe.appchanchitajsf.Util.LogFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class TransaccionBeam  implements Serializable {

private Transaccion transaccion;
private List<Fondo> listaF;
private int fondoaS;
private UploadedFile file;
private String qrCodeImage;

    public TransaccionBeam() throws SQLException, NamingException, IOException {
        this.transaccion=new Transaccion();
        this.transaccion.setResponsable(Auth.UsuarioActivo());
        this.transaccion.setHora();
        this.transaccion.setFecha();
        this.listaF=new ArrayList<>();
        this.listaF=TransaccionDAO.ListarF();
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public List<Fondo> getListaF() {
        return listaF;
    }

    public void setListaF(List<Fondo> listaF) {
        this.listaF = listaF;
    }

    public int getFondoaS() {
        return fondoaS;
    }

    public void setFondoaS(int fondoaS) {
        this.fondoaS = fondoaS;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public String registrarTrab() throws SQLException, NamingException, IOException, WriterException {
        String ruta;
        if(this.fondoaS!=0){
            this.transaccion.setFondoAsociado(obtenerFondoPorId(fondoaS));
        }
        if (this.file!=null){
            LogFile.info("si se guardo el input file");
           ruta= ControlFiles.saveFile(file);
           this.transaccion.setEvidencia(ruta.getBytes());
        }
        TransaccionDAO.RegistarTransaccionBS(this.transaccion);
        this.qrCodeImage=generateQRCode();
        // Generar QR Code

        return null;
    }
    private Fondo obtenerFondoPorId(int id) {
        for (Fondo fondo : listaF) {
            if (fondo.getId() == id) {
                return fondo;
            }
        }

        return null;
    }

    public String generateQRCode() throws WriterException, IOException {
        // Asegúrate de que transaccion tenga los datos necesarios
        StringBuilder qrContent = new StringBuilder();
        qrContent.append("Fondo: ").append(transaccion.getFondoAsociado().getNombre()).append("\n");
        qrContent.append("Responsable: ").append(transaccion.getResponsable().getNombre()).append("\n");
        qrContent.append("cliente nombre: ").append(transaccion.getUsuarioAsociado().getNombre()).append("\n");
        qrContent.append("cliente apellido: ").append(transaccion.getUsuarioAsociado().getApellido()).append("\n");
        qrContent.append("cliente dni: ").append(transaccion.getUsuarioAsociado().getDni()).append("\n");
        qrContent.append("Fecha: ").append(transaccion.getFecha()).append("\n");
        qrContent.append("Hora: ").append(transaccion.getHora()).append("\n");

        // Puedes agregar más campos según sea necesario
        String text = qrContent.toString();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        // Convertir a Base64

        return Base64.getEncoder().encodeToString(pngData);
    }
}
