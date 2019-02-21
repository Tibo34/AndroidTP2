package edu.pokemon.iut.pokedex.architecture.di;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.pokemon.iut.pokedex.data.webservice.PokemonApiClient;

/**
 * Dependencies injection Module for Api calling
 */
@Module
public class ApiModule {
    /**
     * The client allow to request data about Pokemon on Pokeapi.co
     *
     * @return an instance of {@link PokemonApiClient}
     */
    @Provides
    public PokemonApiClient providePokemonApiClient() {
        return new PokemonApiClient();
    }

    /**
     * Singleton for Multi Thread execution.
     *
     * @return an instance of {@link Executor}
     */
    @Provides
    @Singleton
    public Executor provideMultiThreadExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
