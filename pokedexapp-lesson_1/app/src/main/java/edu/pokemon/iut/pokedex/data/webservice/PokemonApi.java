package edu.pokemon.iut.pokedex.data.webservice;

import edu.pokemon.iut.pokedex.data.model.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * {@link retrofit2.Retrofit} api interface<br>
 * This interface allow to specifies the API contract to respect
 */
public interface PokemonApi {

    @GET("pokemon/{pokemonId}")
    Call<Pokemon> getPokemon(@Path("pokemonId") int pokemonId);
}
