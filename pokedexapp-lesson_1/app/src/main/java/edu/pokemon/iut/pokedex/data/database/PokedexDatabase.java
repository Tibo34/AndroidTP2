package edu.pokemon.iut.pokedex.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import javax.inject.Singleton;

import edu.pokemon.iut.pokedex.data.dao.PokemonDao;
import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.model.Sprites;

/**
 * Abstract class for the Database. extends {@link RoomDatabase}<br>
 * You need to declare here any new DAO that you could need to access the database<br>
 * If any change is made on the database, you will need to increment the version<br>
 * Here you can declare any entity as Table for the database<br>
 * Current Table :<br>
 * - {@link Pokemon}<br>
 * - {@link Sprites}<br>
 */
@Singleton
@Database(entities = {Pokemon.class, Sprites.class}, version = 2, exportSchema = false)
public abstract class PokedexDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}