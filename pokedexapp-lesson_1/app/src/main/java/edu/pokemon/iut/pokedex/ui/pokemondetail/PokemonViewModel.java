package edu.pokemon.iut.pokedex.ui.pokemondetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.repository.PokemonRepository;

/**
 * ViewModel for One unique Pokemon
 */
public class PokemonViewModel extends ViewModel {

    private final PokemonRepository pokemonRepository;
    private LiveData<Pokemon> pokemon;
    private LiveData<Integer> idMaxPokemon;

    /**
     * Dagger injects automatically all parameters<br>
     * The ViewModel need the {@link PokemonRepository} to request what it needs
     *
     * @param pokemonRepository {@link PokemonRepository}
     */
    @Inject
    public PokemonViewModel(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Init the ViewModel for a given Pokemon ID
     *
     * @param pokemonId for the pokemon to Observe
     */
    public void init(int pokemonId) {
        //If we already load the pokemon, no need to do it again, the observation will allow us to update
        if (this.pokemon != null) {
            return;
        }
        pokemon = pokemonRepository.getPokemon(pokemonId);
        idMaxPokemon = pokemonRepository.getNumberOfPokemon();
    }

    /**
     * @return a {@link LiveData} of {@link Pokemon} that can be observe to upload the view automatically in case of change
     */
    public LiveData<Pokemon> getPokemon() {
        return this.pokemon;
    }

    /**
     * @return a {@link LiveData} of {@link Integer} to know the number of pokemon which exist for the app
     */
    public LiveData<Integer> getIdMaxPokemon() {
        return idMaxPokemon;
    }

    /**
     * Allow to update the capture status of a pokemon
     * @param pokemon to update
     */
    public void capture(Pokemon pokemon) {
        pokemonRepository.capture(pokemon.capture());
    }
}
