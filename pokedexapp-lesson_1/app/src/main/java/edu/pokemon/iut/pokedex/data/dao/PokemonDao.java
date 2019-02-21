package edu.pokemon.iut.pokedex.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.pokemon.iut.pokedex.data.model.Pokemon;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * DAO that give access to the Pokemon Database
 */
@Dao
public interface PokemonDao {
    /**
     * Insert a pokemon, if it exist already the method will replace it.
     *
     * @param pokemon {@link Pokemon} to save
     */
    @Insert(onConflict = REPLACE)
    void save(Pokemon pokemon);

    /**
     * Query to retrieve a specific pokemon by is ID
     *
     * @param pokemonId of the pokemon to retrieve
     * @return {@link Pokemon} for the given id, {@link LiveData} allow to observe any change in database and notify the observer.
     */
    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    LiveData<Pokemon> load(int pokemonId);

    /**
     * Query to retrieve ALL the pokemon
     *
     * @return {@link List<Pokemon>}, {@link LiveData} allow to observe any change in database and notify the observer.
     */
    @Query("SELECT * FROM pokemon")
    LiveData<List<Pokemon>> loadAll();

    /**
     * Query to retrieve SOME pokemon filtered by their name
     *
     * @param query name or portion of name
     * @return {@link List<Pokemon>}, {@link LiveData} allow to observe any change in database and notify the observer.
     */
    @Query("SELECT * FROM pokemon WHERE name LIKE :query")
    LiveData<List<Pokemon>> loadAllWithFilter(String query);

    /**
     * Query to know if a pokemon already exist or not in the database
     *
     * @param pokemonId of the one to check existence
     * @return id of the pokemon
     */
    @Query("SELECT id from pokemon where id = :pokemonId")
    int hasPokemon(int pokemonId);

    /**
     * Query to know the number of pokemon saved in database
     *
     * @return {@link Integer} number of pokemon, {@link LiveData} allow to observe any change in database and notify the observer.
     */
    @Query("SELECT COUNT(*) FROM pokemon")
    LiveData<Integer> getNumberOfPokemon();

    /**
     * Query to update the status of capture for a pokemon
     *
     * @param pokemon {@link Pokemon} to update
     */
    @Update(onConflict = REPLACE)
    void capture(Pokemon pokemon);

}