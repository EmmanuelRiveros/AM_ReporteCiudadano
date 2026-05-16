package mx.com.itson.am_reporteciudadano;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;


public class FormularioActivity extends AppCompatActivity {

    // 1. Declaramos los nuevos elementos de la interfaz
    private EditText etNombre, etDireccion, etCelular, etCorreo, etDescripcion;
    private Spinner spinnerTipo, spinnerColonia;
    private ImageView imgEvidencia;
    private Button btnCamara, btnEnviar;

    private String fotoBase64 = "";

    // 2. Lanzador de la cámara (se queda igual, esto ya funcionaba perfecto)
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgEvidencia.setImageBitmap(imageBitmap);
                    fotoBase64 = convertirBitmapABase64(imageBitmap);
                }
            }
    );
    private final ActivityResultLauncher<String> permisoCameraLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            granted -> {
                if (granted) {
                    abrirCamara();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Reporte Ciudadano");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Si te marca error la 'v', ignora el padding o asegúrate que tu ScrollView tenga android:id="@+id/main"
            if (v != null) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        // 3. Enlazamos las variables con los IDs de tu nuevo XML
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        spinnerColonia = findViewById(R.id.spinnerColonia);
        etCelular = findViewById(R.id.etCelular);
        etCorreo = findViewById(R.id.etCorreo);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        etDescripcion = findViewById(R.id.etDescripcion);
        imgEvidencia = findViewById(R.id.imgEvidencia);
        btnCamara = findViewById(R.id.btnCamara);
        btnEnviar = findViewById(R.id.btnEnviar);

        // (Ya no necesitamos llenar los Spinners por código porque lo hicimos directo en el XML con el entries="@array/...")

        // 4. Acción del botón de la cámara
        btnCamara.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                permisoCameraLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        // 5. Acción del botón enviar
        btnEnviar.setOnClickListener(v -> {
            // Extraemos todo el texto nuevo
            String nombre = etNombre.getText().toString();
            String direccion = etDireccion.getText().toString();
            String colonia = spinnerColonia.getSelectedItem().toString();
            String celular = etCelular.getText().toString();
            String correo = etCorreo.getText().toString();
            String tipo = spinnerTipo.getSelectedItem().toString();
            String descripcion = etDescripcion.getText().toString();

            // Validación básica
            if (nombre.isEmpty() || direccion.isEmpty() || fotoBase64.isEmpty()) {
                Toast.makeText(FormularioActivity.this, "Faltan datos o tomar la foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FormularioActivity.this, "Procesando datos para el servidor...", Toast.LENGTH_SHORT).show();

                // Creamos el paquete exacto que pide el profesor
                ReporteRequest nuevoReporte = new ReporteRequest(
                        nombre, direccion, colonia, celular, correo, tipo, descripcion, fotoBase64
                );

                String tokenBearer = "Bearer a0f4dcad-5903-482f-8982-88ec8bc6156e"; // Reemplaza por el actual si cambió

                // Llamamos a Retrofit y le mandamos el token y los datos
                ApiService apiService = RetrofitClient.getApiService();
                retrofit2.Call<Void> call = apiService.enviarReporte(tokenBearer, nuevoReporte);

                // Lo enviamos por internet
                call.enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(FormularioActivity.this, "¡Reporte enviado exitosamente al servidor!", Toast.LENGTH_LONG).show();
                            finish(); // Cierra esta pantalla y regresa al menú
                        } else {
                            // !!! AQUÍ QUEDÓ TU NUEVO CÓDIGO INTEGRADO !!!
                            try {
                                String errorBody = response.errorBody().string();
                                Toast.makeText(FormularioActivity.this, "Error " + response.code() + ": " + errorBody, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(FormularioActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        Toast.makeText(FormularioActivity.this, "Fallo de conexión. Revisa tu internet: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private String convertirBitmapABase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // ← PNG, no JPEG
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        return "data:image/png;base64," + base64;
    }
}