package es.ieslavereda.android_rvgot_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ieslavereda.android_rvgot_base.model.Personaje;
import es.ieslavereda.android_rvgot_base.model.PersonajeRepository;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Personaje> personajes;

    public AdaptadorRecyclerView(Context context){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        personajes = PersonajeRepository.getInstance().getAll();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.simple_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.escudo.setImageResource(personajes.get((position)).getCasa().getEscudo());
        holder.nombre.setText(personajes.get(position).getNombre());
        holder.casa.setText(personajes.get(position).getCasa().getNombre());
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView escudo;
        private TextView nombre,casa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            escudo = itemView.findViewById(R.id.imageView);
            nombre = itemView.findViewById(R.id.nombre_personaje);
            casa = itemView.findViewById(R.id.casa);

        }
    }
}
