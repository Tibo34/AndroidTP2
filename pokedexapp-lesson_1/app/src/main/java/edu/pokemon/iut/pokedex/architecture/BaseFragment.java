package edu.pokemon.iut.pokedex.architecture;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import edu.pokemon.iut.pokedex.PokedexApp;
import edu.pokemon.iut.pokedex.R;

/**
 * Base class for every Fragment, it provides some custom behavior for all of the fragments.<br>
 * - {@link NavigationManager} is inject here via {@link PokedexApp} and {@link edu.pokemon.iut.pokedex.architecture.di.DaggerPokemonComponent}<br>
 * - {@link ViewModelProvider.Factory} is inject here via {@link PokedexApp} and {@link edu.pokemon.iut.pokedex.architecture.di.DaggerPokemonComponent}<br>
 * - The {@value DEFAULT_TITLE} of the app is use in the action_bar it can be change by setTitle<br>
 * - {@link ButterKnife} bind's all views at onViewCreated<br>
 * - The action bar is init at onViewCreated, the buttonHome is shown by default, at it use the default title
 */
public class BaseFragment extends Fragment {
    private static final String DEFAULT_TITLE = "Pokedex";
    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    @Inject
    protected NavigationManager navigationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PokedexApp.app().component().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initActionBar(true, getString(R.string.app_name));
    }

    /**
     * Initializes the ActionBar
     *
     * @param showHomeButton true if we want to show the Home/Arrow icon, false otherwise
     * @param title          the text we want to show on the action bar, if null we show the default one
     */
    protected void initActionBar(boolean showHomeButton, String title) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(showHomeButton);
                supportActionBar.setTitle(title != null ? title : DEFAULT_TITLE);
            }
        }
    }

    /**
     * Allow to change the action bar title at demand
     *
     * @param title the new title, if null we use the default one
     */
    protected void setTitle(String title) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(title != null ? title.toUpperCase() : DEFAULT_TITLE);
            }
        }
    }
}