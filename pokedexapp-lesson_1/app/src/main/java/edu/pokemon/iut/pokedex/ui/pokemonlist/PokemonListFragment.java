package edu.pokemon.iut.pokedex.ui.pokemonlist;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import edu.pokemon.iut.pokedex.R;
import edu.pokemon.iut.pokedex.architecture.BaseFragment;
import edu.pokemon.iut.pokedex.data.model.Pokemon;

/**
 * Fragment to show a List of Pokemon
 */
@SuppressWarnings("WeakerAccess")
public class PokemonListFragment extends BaseFragment implements PokemonAdapter.CaptureListener {

    private static final String TAG = PokemonListFragment.class.getSimpleName();


    /* VIEWS */
    @BindView(R.id.rv_pokemon_list)
    protected RecyclerView pokemonListView;

    /* ATTRIBUTES */
    private PokemonListViewModel viewModel;
    private PokemonAdapter adapter;
    private View rootView;

    /**
     * @param query to filter the pokemon list
     * @return newInstance of SampleFragment
     */
    public static PokemonListFragment newInstance(CharSequence query) {
        PokemonListFragment pokemonListFragment = new PokemonListFragment();
        return pokemonListFragment;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.pokemon_list_layout, null);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar(false, null);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        pokemonListView.setHasFixedSize(true);
        pokemonListView.setItemViewCacheSize(20);

        // use a linear layout manager if in portrait or a grid layout manager in landscape or tablet view
        int orientation = Configuration.ORIENTATION_PORTRAIT;
        if (getActivity() != null && getActivity().getResources() != null) {
            orientation = getActivity().getResources().getConfiguration().orientation;
        }

        LinearLayoutManager mLayoutManager;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new LinearLayoutManager(getContext());
        } else {
            mLayoutManager = new GridLayoutManager(getContext(), 3);
        }

        pokemonListView.setLayoutManager(mLayoutManager);
        adapter = new PokemonAdapter(getContext(), navigationManager, this);
        pokemonListView.setAdapter(adapter);
        if(pokemonListView.getAdapter() != null) {
            pokemonListView.getAdapter().notifyDataSetChanged();
        }

        //Initialisation and observation of the ViewModel for this screen
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PokemonListViewModel.class);
        CharSequence query = null;
        viewModel.init(query);

        viewModel.getPokemons().observe(this, pokemonList -> {
            adapter.setData(pokemonList);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onCapture(Pokemon pokemon) {
    }
}