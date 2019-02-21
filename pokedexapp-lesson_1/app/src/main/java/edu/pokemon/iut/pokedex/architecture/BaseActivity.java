package edu.pokemon.iut.pokedex.architecture;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import edu.pokemon.iut.pokedex.PokedexApp;
import edu.pokemon.iut.pokedex.R;

/**
 * Base activity for all the Activities, it provides some common operation for all of the sub-activities.<br>
 * - Set up the default {@link Toolbar}, NavigationOnClickListener is set with onBackPressed from activity<br>
 * - onBackPressed from Activity is set with navigateBack from {@link NavigationManager}<br>
 * - {@link NavigationManager} is inject here via {@link PokedexApp} and {@link edu.pokemon.iut.pokedex.architecture.di.DaggerPokemonComponent}<br>
 * - The main content of the activity is set via setContentView on R.layout.activity_base<br>
 * - {@link ButterKnife} bind's all views on setContentView<br>
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Inject
    protected NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PokedexApp.app().component().inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> this.onBackPressed());

        FrameLayout mContentLayout = findViewById(R.id.content);
        // Get an inflater
        getLayoutInflater().inflate(layoutResID, mContentLayout);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.navigationManager.navigateBack(this);
    }
}