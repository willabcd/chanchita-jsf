package utp.edu.pe.appchanchitajsf.Beans;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@ManagedBean
@RequestScoped
public class QRCodeBean {

    private String texto;

    // Getter y Setter para `texto`
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Método para generar el código QR
    public void generateQRCode() {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 200, 200);

            // Crear una imagen del código QR
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Escribir la imagen como un flujo de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Enviar la imagen como respuesta HTTP
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("image/png");
            response.setContentLength(imageBytes.length);
            OutputStream out = response.getOutputStream();
            out.write(imageBytes);
            out.flush();
            out.close();
            facesContext.responseComplete();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
