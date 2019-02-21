package edu.pokemon.iut.pokedex.architecture.di;

import dagger.Binds;
import dagger.Module;
import edu.pokemon.iut.pokedex.data.repository.PokemonRepository;
import edu.pokemon.iut.pokedex.data.repository.PokemonRepositoryImpl;

/**
 * Dependencies injection Module for accessing the Data layer through Repository
 * Add any new repository needed here
 */
@Module
public abstract class RepositoryModule {
    /**
     * Main repository to access distant services and database of pokemon
     *
     * @param pokemonRepositoryImpl an actual implementation of {@link PokemonRepository} interface
     * @return an interface of {@link PokemonRepository} who refers to the param.
     */
    @Binds
    abstract PokemonRepository bindsPokemonRepository(PokemonRepositoryImpl pokemonRepositoryImpl);
}
