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

import java.util.Collections;

import es.ieslavereda.android_rvgot_base.model.Personaje;
import es.ieslavereda.android_rvgot_base.model.PersonajeRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recycler;

    private PersonajeRepository personajes;

    private ImageButton addButton;
    private Switch switchOdenar;
    private Button list;
    private Button grid;
    private boolean isLinear;
    AdaptadorRecyclerView adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add);
        switchOdenar = findViewById(R.id.switch_ordenar);
        list = findViewById(R.id.list_button);
        grid = findViewById(R.id.grid_button);
        isLinear = true;

        personajes = PersonajeRepository.getInstance();
        personajes.sort(Personaje.SORT_BY_NAME);

        adaptador = new AdaptadorRecyclerView(this);
        adaptador.setOnClickListener(this);
        recycler.setAdapter(adaptador);
        updateRecycle();

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

        switchOdenar.setOnCheckedChangeListener((buttonview, isChecked) -> {
            if (isChecked) {
                personajes.sort(Personaje.SORT_BY_CASA);
                adaptador.notifyDataSetChanged();
            } else {
                personajes.sort(Personaje.SORT_BY_NAME);
                adaptador.notifyDataSetChanged();
            }
        });

        list.setOnClickListener(view -> {
            isLinear = true;
            updateRecycle();
        });
        grid.setOnClickListener(view -> {
            isLinear = false;
            updateRecycle();
        });

        ItemTouchHelper objetoDeslizar = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        int posDestino = target.getAdapterPosition();
                        int posicionInicial = viewHolder.getAdapterPosition();
                        Personaje personaje = PersonajeRepository.getInstance().get(posicionInicial);

                        PersonajeRepository.getInstance().remove(personaje);
                        PersonajeRepository.getInstance().add(posDestino, personaje);

                        adaptador.notifyItemMoved(posicionInicial, posDestino);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int posicion = viewHolder.getAdapterPosition();
                        Personaje personaje = PersonajeRepository.getInstance().get(posicion);
                        PersonajeRepository.getInstance().remove(personaje);
                        adaptador.notifyItemRemoved(posicion);
                        Snackbar.make(recycler, "Borrado el personaje: " + personaje.getNombre(), Snackbar.LENGTH_SHORT).setAction("Undo", view -> {
                            PersonajeRepository.getInstance().add(posicion, personaje);
                            adaptador.notifyItemInserted(posicion);
                        }).show();

                    }
                }
        );

        objetoDeslizar.attachToRecyclerView(recycler);
    }

    @Override
    public void onClick(View view) {
        Personaje personaje = PersonajeRepository.getInstance().get(recycler.getChildAdapterPosition(view));
        Intent intent = new Intent(this, ActivityInfo.class);
        intent.putExtra("Personaje", personaje);
        startActivity(intent);
    }

    public void updateRecycle(){
        if (isLinear){
            adaptador.setLayout_displayed(R.layout.simple_element_list);
            recycler.setLayoutManager(new LinearLayoutManager(this));
        } else {
            adaptador.setLayout_displayed(R.layout.simple_element_grid);
            recycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recycler.getRecycledViewPool().clear();
    }
}