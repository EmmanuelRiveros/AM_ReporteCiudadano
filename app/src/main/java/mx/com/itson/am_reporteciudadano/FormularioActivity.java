package mx.com.itson.am_reporteciudadano;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class FormularioActivity extends AppCompatActivity {

    // 1. Declaramos los elementos de la interfaz
    private EditText etNombre, etDireccion, etContacto;
    private Spinner spinnerCategoria;
    private ImageView imgEvidencia;
    private Button btnTomarFoto, btnEnviarReporte;

    // Variable para guardar la foto transformada a texto
    private String fotoBase64 = "";

    // 2. Preparamos el "Lanzador" de la cámara
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Extraemos la foto capturada
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    // La mostramos en el cuadro gris de la pantalla
                    imgEvidencia.setImageBitmap(imageBitmap);

                    // La convertimos a Base64 usando nuestro método especial
                    fotoBase64 = convertirBitmapABase64(imageBitmap);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario);

        // Ajuste para la barra de estado
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Enlazamos las variables con los IDs de tu XML
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        etContacto = findViewById(R.id.etContacto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        imgEvidencia = findViewById(R.id.imgEvidencia);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnEnviarReporte = findViewById(R.id.btnEnviarReporte);

        // 4. Llenar el Spinner con las categorías
        String[] categorias = {"Alumbrado Público", "Baches", "Animales Callejeros", "Basura o Escombro", "Fumigación", "Fugas o Drenaje", "Otro Asunto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoria.setAdapter(adapter);

        // 5. Acción del botón de la cámara
        btnTomarFoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(takePictureIntent);
        });

        // 6. Acción del botón enviar (Validación básica por ahora)
        btnEnviarReporte.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String direccion = etDireccion.getText().toString();

            if (nombre.isEmpty() || direccion.isEmpty() || fotoBase64.isEmpty()) {
                // Si falta texto o la foto, mostramos un mensajito de error
                Toast.makeText(FormularioActivity.this, "Faltan datos o tomar la foto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FormularioActivity.this, "¡Todo listo para conectar a la API!", Toast.LENGTH_SHORT).show();
                // Aquí pondremos el código de red (Retrofit) más adelante
            }
        });
    }

    // Método que transforma la imagen a Base64
    private String convertirBitmapABase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Comprimimos al 80% para que el texto no sea inmenso
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}