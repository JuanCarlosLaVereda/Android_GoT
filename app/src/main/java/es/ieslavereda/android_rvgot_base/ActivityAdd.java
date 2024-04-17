package es.ieslavereda.android_rvgot_base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import es.ieslavereda.android_rvgot_base.model.Casa;
import es.ieslavereda.android_rvgot_base.model.Personaje;

public class ActivityAdd extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EditText nombre = findViewById(R.id.nombre);
        Spinner casas = findViewById(R.id.casas);
        Button aceptar = findViewById(R.id.aceptar);
        Button cancelar = findViewById(R.id.cancelar);

        //ArrayAdapter<Casa> adaptadorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Casa.values());
        ArrayAdapter<Casa> adaptadorSpinner = new CustomArrayAdapter<>(this, R.layout.custom_spinner, Casa.values());
        casas.setAdapter(adaptadorSpinner);

        cancelar.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED);
            finish();
        });

        aceptar.setOnClickListener(view -> {
            Intent intent = new Intent();
            String name = nombre.getText().toString();
            Casa casa = (Casa)casas.getSelectedItem();
            intent.putExtra("Personaje", new Personaje(name, casa));
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
