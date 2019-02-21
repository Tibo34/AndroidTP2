package edu.pokemon.iut.pokedex.architecture.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.pokemon.iut.pokedex.architecture.viewmodel.ViewModelFactory;
import edu.pokemon.iut.pokedex.architecture.viewmodel.ViewModelKey;
import edu.pokemon.iut.pokedex.ui.pokemondetail.PokemonDetailFragment;
import edu.pokemon.iut.pokedex.ui.pokemondetail.PokemonViewModel;
import edu.pokemon.iut.pokedex.ui.pokemonlist.PokemonListViewModel;

/**
 * Dependencies injection Module for accessing the ViewModel objects for the app
 * Add any new ViewModel here to allow it's access on any class who needs it.
 */
@Module
public abstract class ViewModelModule {

    /**
     * Main factory to construct viewModel with non empty constructor.
     *
     * @param viewModelFactory an actual implementation of {@link ViewModelProvider.Factory} interface
     * @return an interface of {@link ViewModelProvider.Factory} who refers to the param.
     */
    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);


    /**
     * Returns a ViewModel to manipulate a list of Pokemon
     *
     * @param pokemonListViewModel actual implementation of abstract class {@link ViewModel}
     * @return an instance of abstract class {@link ViewModel}
     */
    @Binds
    @IntoMap
    @ViewModelKey(PokemonListViewModel.class)
    abstract ViewModel bindsPokemonListViewModel(PokemonListViewModel pokemonListViewModel);

    // TODO 20) AJOUTER UNE METHODE bindsPokemonDetailViewModel POUR PokemonViewModel
    @Binds
    @IntoMap
    @ViewModelKey(PokemonViewModel.class)
    abstract ViewModel bindsPokemonDetailViewModel(PokemonViewModel pokemonViewModel);
}