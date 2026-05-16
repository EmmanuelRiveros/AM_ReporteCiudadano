package mx.com.itson.am_reporteciudadano;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaReportesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReportes;
    private ReporteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_reportes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (v != null) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        // 1. Inicializamos el RecyclerView
        recyclerViewReportes = findViewById(R.id.recyclerViewReportes);
        recyclerViewReportes.setLayoutManager(new LinearLayoutManager(this));

        // 2. Creamos la lista vacía
        List<ReporteRequest> reportesFalsos = new ArrayList<>();

        // 3. Forzamos el llenado manual de los 2 datos de prueba (Asegúrate de que existan estas líneas)
        reportesFalsos.add(new ReporteRequest(
                "Julio Nava",
                "Calle Mar Azul #20",
                "LUIS DONALDO COLOSIO",
                "6221002760",
                "julio.nava92307@potros.itson.edu.mx",
                "ALUMBRADO PÚBLICO",
                "Está oscuro, no se ve bien por las noches la lámpara.",
                "" // Sin foto por ahora
        ));

        reportesFalsos.add(new ReporteRequest(
                "Carlos Mendoza",
                "Av. Serdán 300",
                "CENTRO",
                "6221112233",
                "carlos@correo.com",
                "BACHES",
                "Bache profundo que abarca medio carril.",
                ""
        ));

        // 4. Conectamos la lista con el adaptador
        adapter = new ReporteAdapter(reportesFalsos);
        recyclerViewReportes.setAdapter(adapter);

        // 5. El Toast de confirmación de tamaño
        Toast.makeText(this, "Datos cargados: " + reportesFalsos.size(), Toast.LENGTH_LONG).show();
    }
}