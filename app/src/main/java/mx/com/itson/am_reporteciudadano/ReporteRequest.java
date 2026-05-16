package mx.com.itson.am_reporteciudadano;

public class ReporteRequest {
    // Tienen que llamarse EXACTAMENTE igual al JSON del profesor
    private String nombre_interesado;
    private String direccion;
    private String colonia;
    private String celular;
    private String correo;
    private String tipo;
    private String descripcion;
    private String imagen; // Aquí va el Base64

    public String getNombre_interesado() {
        return nombre_interesado;
    }

    public void setNombre_interesado(String nombre_interesado) {
        this.nombre_interesado = nombre_interesado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    // Constructor actualizado
    public ReporteRequest(String nombre_interesado, String direccion, String colonia,
                          String celular, String correo, String tipo,
                          String descripcion, String imagen) {
        this.nombre_interesado = nombre_interesado;
        this.direccion = direccion;
        this.colonia = colonia;
        this.celular = celular;
        this.correo = correo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

}