package edu.pokemon.iut.pokedex.architecture.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.pokemon.iut.pokedex.architecture.BaseActivity;
import edu.pokemon.iut.pokedex.architecture.BaseFragment;
import edu.pokemon.iut.pokedex.ui.pokemondetail.PokemonDetailFragment;
import edu.pokemon.iut.pokedex.ui.pokemonlist.PokemonListFragment;

/**
 * Main component of the app for Dependency Injection
 * Regroup all the modules needed for each Class.
 * Add a line with any new class who needs injection to access the different object provide by the modules.
 */
@Singleton
@Component(modules = {RepositoryModule.class, ViewModelModule.class, ApiModule.class, DatabaseModule.class, NavigationModule.class})
public interface PokemonComponent {
    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(PokemonListFragment pokemonListFragment);

    // TODO 19) AJOUTER UNE METHODE inject POUR PokemonDetailFragment
    void inject(PokemonDetailFragment pokemonDetail);

}
