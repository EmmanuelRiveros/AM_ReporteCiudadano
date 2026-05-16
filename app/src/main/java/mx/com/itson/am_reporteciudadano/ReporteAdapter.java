package mx.com.itson.am_reporteciudadano;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ViewHolder> {

    private List<ReporteRequest> listaReportes;

    public ReporteAdapter(List<ReporteRequest> listaReportes) {
        this.listaReportes = listaReportes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reporte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReporteRequest reporte = listaReportes.get(position);

        // Conectamos los nuevos campos del profesor
        holder.tvCategoria.setText(reporte.getTipo());
        holder.tvDireccion.setText(reporte.getColonia() + " - " + reporte.getDireccion());

        // Decodificador de imagen Base64
        try {
            if (reporte.getImagen() != null && !reporte.getImagen().isEmpty()) {
                byte[] decodedString = Base64.decode(reporte.getImagen(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imgMiniatura.setImageBitmap(decodedByte);
            } else {
                holder.imgMiniatura.setImageResource(android.R.drawable.ic_menu_gallery); // Icono por defecto si no hay foto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaReportes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMiniatura;
        TextView tvCategoria;
        TextView tvDireccion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMiniatura = itemView.findViewById(R.id.imgMiniatura);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaItem);
            tvDireccion = itemView.findViewById(R.id.tvDireccionItem);
        }
    }
}