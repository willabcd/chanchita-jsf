package utp.edu.pe.appchanchitajsf.Clases;

public class Cuenta {
    private int id_cuenta;
    private String NumeroCuenta;

    public Cuenta() {
    }

    public Cuenta(int id_cuenta, String numeroCuenta) {
        this.id_cuenta = id_cuenta;
        NumeroCuenta = numeroCuenta;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        NumeroCuenta = numeroCuenta;
    }
}
