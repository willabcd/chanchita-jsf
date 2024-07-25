package utp.edu.pe.appchanchitajsf.Beans;

import utp.edu.pe.appchanchitajsf.Clases.Persona;
import utp.edu.pe.appchanchitajsf.Negocio.UserDAO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@FacesValidator("utp.edu.pe.appchanchitajsf.Beans.Validaciones")
public class Validaciones implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String correoIngresado = (String) o;

        try {
            // Obtener lista de correos ya existentes
            List<Persona> listaCorreos = UserDAO.Credenciales();

            // Verificar si el correo ingresado ya existe
            for (Persona persona : listaCorreos) {
                if (persona.getCorreo().equals(correoIngresado)) {
                    // Lanzar una excepción de validación si el correo ya existe
                    throw new ValidatorException(
                            new javax.faces.application.FacesMessage(
                                    javax.faces.application.FacesMessage.SEVERITY_ERROR,
                                    "Error",
                                    "Este correo ya está registrado."
                            )
                    );
                }
            }
        } catch (IOException | SQLException | NamingException e) {
            e.printStackTrace();
            throw new ValidatorException(new javax.faces.application.FacesMessage(
                    javax.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo verificar el correo."
            ));
        }
    }
}
