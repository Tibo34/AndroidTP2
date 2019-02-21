package edu.pokemon.iut.pokedex.data.service;

import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.webservice.PokemonApi;
import edu.pokemon.iut.pokedex.data.webservice.PokemonApiClient;
import retrofit2.Response;

/**
 * This class give access to a service that return information about {@link Pokemon} <br>
 * It abstract the source thanks to {@link PokemonApiClient} <br>
 * The only condition is to respect the {@link Pokemon} contract for {@link com.google.gson.Gson} automatic transformation.
 */
public class PokemonService {
    private static final String TAG = PokemonService.class.getSimpleName();
    private final PokemonApi pokemonApi;

    /**
     * Dagger injects the parameter automatically<br>
     * {@link PokemonApiClient} give access to the data source out-app
     *
     * @param pokemonApiClient {@link PokemonApiClient} abstract the external services which provides Pokemon data
     */
    @SuppressWarnings("WeakerAccess")
    @Inject
    protected PokemonService(PokemonApiClient pokemonApiClient) {
        pokemonApi = pokemonApiClient.getPokemonApi();
    }

    /**
     * Call the {@link PokemonApiClient} to retrieve one pokemon
     *
     * @param pokemonId of the pokemon to retrieve
     * @return {@link Pokemon} from the api
     */
    public Pokemon getPokemon(int pokemonId) {
        Response<Pokemon> response = null;
        try {
            //Execute directly the request
            response = pokemonApi.getPokemon(pokemonId).execute();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        Pokemon pokemon = null;
        //Mapping the response in a new instance of Pokemon
        if (response != null && response.body() != null) {
            pokemon = new Pokemon();
            pokemon.setName(Objects.requireNonNull(response.body()).getName());
            pokemon.setWeight(Objects.requireNonNull(response.body()).getWeight());
            pokemon.setHeight(Objects.requireNonNull(response.body()).getHeight());
            pokemon.setId(Objects.requireNonNull(response.body()).getId());
            pokemon.setBaseExperience(Objects.requireNonNull(response.body()).getBaseExperience());
            pokemon.setDefault(Objects.requireNonNull(response.body()).isDefault());
            pokemon.setOrder(Objects.requireNonNull(response.body()).getOrder());
            pokemon.setSprites(Objects.requireNonNull(response.body()).getSprites());
            pokemon.getSprites().setId(pokemon.getId());
            pokemon.setSpritesString(pokemon.getSprites().getFrontDefault());
            pokemon.setTypes(Objects.requireNonNull(response.body()).getTypes());
        }

        return pokemon;
    }
}
