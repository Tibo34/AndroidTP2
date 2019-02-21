package edu.pokemon.iut.pokedex;

import android.app.Application;

import edu.pokemon.iut.pokedex.architecture.di.ApiModule;
import edu.pokemon.iut.pokedex.architecture.di.DaggerPokemonComponent;
import edu.pokemon.iut.pokedex.architecture.di.DatabaseModule;
import edu.pokemon.iut.pokedex.architecture.di.PokemonComponent;

/**
 * Core for the Dagger Dependency injections, and Database migration
 */
public class PokedexApp extends Application {

    private PokemonComponent pokemonComponent;
    private static PokedexApp application;

    @Override
    public void onCreate() {
        super.onCreate();

        //Init of injection dependency
        application = this;
        pokemonComponent = DaggerPokemonComponent.builder()
                .databaseModule(new DatabaseModule(getApplicationContext()))
                .apiModule(new ApiModule())
                .build();
    }

    public static PokedexApp app() {
        return application;
    }

    //allows activities to retrieve AppComponent thanks to .getApplication().appComponent()
    public PokemonComponent component() {
        return pokemonComponent;
    }
}
