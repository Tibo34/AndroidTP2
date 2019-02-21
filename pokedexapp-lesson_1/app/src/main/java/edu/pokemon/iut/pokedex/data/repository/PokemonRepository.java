package edu.pokemon.iut.pokedex.data.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.pokemon.iut.pokedex.data.model.Pokemon;

/**
 * Repository of Pokemon.<br>
 * This class allows to manipulate {@link Pokemon} from any sources.<br>
 * It abstract the data sources for the upper layer.<br>
 */
public interface PokemonRepository {
    /**
     * Gives all the pokemon known for the app<br>
     * You can filter the results by given a {@link CharSequence} for the name (partial or complete)<br>
     * Return a {@link LiveData}<{@link List}<{@link Pokemon}> that can be observe to know any change in the data.
     *
     * @param query {@link CharSequence} can be null or empty
     * @return {@link LiveData}<{@link List}<{@link Pokemon}> to observe
     */
    LiveData<List<Pokemon>> getPokemons(CharSequence query);

    /**
     * Gives the pokemon known for the app with an id<br>
     * Return a {@link LiveData<Pokemon>} that can be observe to know any change in the data.
     *
     * @param pokemonId for the {@link Pokemon} you want
     * @return {@link LiveData<Pokemon>} to observe
     */
    LiveData<Pokemon> getPokemon(int pokemonId);

    /**
     * Change the Capture status of a given pokemon.
     *
     * @param pokemon {@link Pokemon} to update capture status
     */
    void capture(Pokemon pokemon);

    /**
     * Count of all Pokemon Known by the app.
     *
     * @return {@link LiveData<Integer>} to observe if any pokemon is added and updates the count
     */
    LiveData<Integer> getNumberOfPokemon();

    /**
     * Change the max pokemon we want
     *
     * @param maxIdPokemon we want
     */
    void setMaxIdPokemon(int maxIdPokemon);
}
