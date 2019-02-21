package edu.pokemon.iut.pokedex.ui.pokemonlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.repository.PokemonRepository;

/**
 * ViewModel for a list of Pokemon
 */
public class PokemonListViewModel extends ViewModel {
    private final PokemonRepository pokemonRepository;
    private LiveData<List<Pokemon>> pokemonList;

    /**
     * Dagger injects automatically all parameters<br>
     * The ViewModel need the {@link PokemonRepository} to request what it needs
     *
     * @param pokemonRepository {@link PokemonRepository}
     */
    @Inject
    public PokemonListViewModel(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Init the ViewModel for a given query(based on pokemon name), if empty or null you get all pokemon
     *
     * @param query to filter which pokemon to retrieve
     */
    public void init(CharSequence query) {
        if (this.pokemonList != null) {
            return;
        }
        pokemonList = pokemonRepository.getPokemons(query);
    }

    /**
     * @return a {@link LiveData} of a {@link List} of {@link Pokemon} that can be observe to upload the view automatically in case of change
     */
    public LiveData<List<Pokemon>> getPokemons() {
        return this.pokemonList;
    }

    /**
     * Allow to update the capture status of a pokemon
     * @param pokemon to update
     */
    public void capture(Pokemon pokemon) {
        pokemonRepository.capture(pokemon.capture());
    }
}
