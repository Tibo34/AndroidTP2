package edu.pokemon.iut.pokedex.architecture.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import edu.pokemon.iut.pokedex.data.dao.PokemonDao;
import edu.pokemon.iut.pokedex.data.database.PokedexDatabase;

/**
 * Dependencies injection Module for accessing the database
 */
@Module
public class DatabaseModule {
    // The context is used to build the database
    private final Context context;

    /**
     * Constructor of the module called by Dagger to provide the context
     *
     * @param context used to build the database
     */
    public DatabaseModule(Context context) {
        this.context = context;
    }

    /**
     * Allow the use of the database of Pokemon
     *
     * @return an instance of {@link PokedexDatabase}
     */
    @Provides
    public PokedexDatabase providesPokedexDatabase() {
        return Room.databaseBuilder(context,
                PokedexDatabase.class, "pokedex-database")
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Allow the use of the {@link PokemonDao} to access the database through database request
     *
     * @param pokedexDatabase wich we want to access
     * @return an instance of {@link PokemonDao}
     */
    @Provides
    @Inject
    public PokemonDao providesPokemonDao(PokedexDatabase pokedexDatabase) {
        return pokedexDatabase.pokemonDao();
    }
}
