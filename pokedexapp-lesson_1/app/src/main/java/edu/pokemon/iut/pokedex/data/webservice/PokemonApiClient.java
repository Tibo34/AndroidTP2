package edu.pokemon.iut.pokedex.data.webservice;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class prepare {@link Retrofit} object to call the API abroad.<br>
 *
 * @see <a href="https://pokeapi.co/">https://pokeapi.co/</a>
 */
public class PokemonApiClient {
    private final Retrofit retrofit;

    public PokemonApiClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .client(okHttpClientBuilder.build())
                .build();
    }

    public PokemonApi getPokemonApi() {
        return retrofit.create(PokemonApi.class);
    }
}
