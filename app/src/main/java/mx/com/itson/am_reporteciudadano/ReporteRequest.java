package mx.com.itson.am_reporteciudadano;

public class ReporteRequest {
    private String nombre;
    private String direccion;
    private String contacto;
    private String categoria;
    private String fotoBase64;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    // Constructor
    public ReporteRequest(String nombre, String direccion, String contacto, String categoria, String fotoBase64) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.contacto = contacto;
        this.categoria = categoria;
        this.fotoBase64 = fotoBase64;
    }

}