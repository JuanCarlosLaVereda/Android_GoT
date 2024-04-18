package es.ieslavereda.android_rvgot_base;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import es.ieslavereda.android_rvgot_base.model.Personaje;

public class ActivityInfo extends AppCompatActivity {

    private TextView casa;
    private TextView nombre;
    private ImageView escudo;
    private Button volver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);
        Bundle extras = getIntent().getExtras();
        Personaje personaje = (Personaje)extras.get("Personaje");

        casa = findViewById(R.id.casa_more_info);
        nombre = findViewById(R.id.nombre_more_info);
        escudo = findViewById(R.id.escudo_more_info);
        volver = findViewById(R.id.volver_more_info);

        nombre.setText(personaje.getNombre());
        casa.setText(personaje.getCasa().getNombre());
        escudo.setImageResource(personaje.getCasa().getEscudo());

        volver.setOnClickListener(view -> {
            finish();
        });
    }
}
