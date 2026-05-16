package mx.com.itson.am_reporteciudadano;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("reporte.php")
    Call<Void> enviarReporte(
            @Header("Authorization") String token,
            @Body ReporteRequest reporte
    );
}