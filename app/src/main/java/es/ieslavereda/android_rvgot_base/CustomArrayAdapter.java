package es.ieslavereda.android_rvgot_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter<T extends Listable> extends ArrayAdapter<T>{
    private int resource;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, new ArrayList<>(List.of(objects)));
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitems = convertView;
        if (listitems == null){
            listitems = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }

        T item = getItem(position);

        ImageView casa = listitems.findViewById(R.id.imagenCasa);
        TextView nombre = listitems.findViewById(R.id.textoCasaSpinner);

        casa.setImageResource(item.getDrawableImage());
        nombre.setText(item.getDescription());

        return listitems;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitems = convertView;
        if (listitems == null){
            listitems = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }

        T item = getItem(position);

        ImageView casa = listitems.findViewById(R.id.imagenCasa);
        TextView nombre = listitems.findViewById(R.id.textoCasaSpinner);

        casa.setImageResource(item.getDrawableImage());
        nombre.setText(item.getDescription());

        return listitems;
    }
}
