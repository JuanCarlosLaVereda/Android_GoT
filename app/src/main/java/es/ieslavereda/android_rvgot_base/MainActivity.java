package es.ieslavereda.android_rvgot_base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.UserPresenceUnavailableException;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import es.ieslavereda.android_rvgot_base.model.Personaje;
import es.ieslavereda.android_rvgot_base.model.PersonajeRepository;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recycler;

    private PersonajeRepository personajes;

    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add);

        personajes = PersonajeRepository.getInstance();

        AdaptadorRecyclerView adaptador = new AdaptadorRecyclerView(this);
        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));

        ActivityResultLauncher<Intent> resoultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_CANCELED){
                        Toast.makeText(this, "Alta cancelada", Toast.LENGTH_SHORT);
                    } else if (result.getResultCode()==RESULT_OK){
                        Personaje personaje = (Personaje) result.getData().getExtras().get("Personaje");
                        PersonajeRepository.getInstance().add(personaje);
                        adaptador.notifyDataSetChanged();
                    }
                }
        );

        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityAdd.class);
            resoultLauncher.launch(intent);
        });
    }
}