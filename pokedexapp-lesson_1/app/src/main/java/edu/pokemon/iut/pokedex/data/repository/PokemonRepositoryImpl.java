package edu.pokemon.iut.pokedex.data.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.pokemon.iut.pokedex.data.dao.PokemonDao;
import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.service.PokemonService;

/**
 * Actual implementation of {@link PokemonRepository}.
 */
@Singleton
public class PokemonRepositoryImpl implements PokemonRepository {

    private final PokemonService pokemonService;
    private final PokemonDao pokemonDao;
    private final Executor executor;
    private int maxIdPokemon = 807;

    @Override
    public void setMaxIdPokemon(int maxIdPokemon){
        this.maxIdPokemon = maxIdPokemon;
    }

    /**
     * Dagger inject all the needed parameters to instantiate the object.
     *
     * @param pokemonService {@link PokemonService} give access pokemon services.
     * @param pokemonDao     {@link PokemonDao} give access to the in-app database.
     * @param executor       {@link Executor} to multi-thread read services and write database.
     */
    @Inject
    public PokemonRepositoryImpl(PokemonService pokemonService, PokemonDao pokemonDao, Executor executor) {
        this.pokemonService = pokemonService;
        this.pokemonDao = pokemonDao;
        this.executor = executor;
    }

    @Override
    public LiveData<List<Pokemon>> getPokemons(CharSequence query) {
        //For now we set the number of pokemon retrieve by hand.
        refreshPokemon(1, maxIdPokemon);

        //If any query was passed we used the query to filter the results, else we retrieve all pokemon
        if (query != null) {
            return pokemonDao.loadAllWithFilter(query.toString() + "%");
        } else {
            return pokemonDao.loadAll();
        }
    }

    @Override
    public LiveData<Pokemon> getPokemon(int pokemonId) {
        //When we ask for a particular pokemon we try to refresh it in the database.
        refreshPokemon(pokemonId, pokemonId);

        return pokemonDao.load(pokemonId);
    }

    @Override
    public LiveData<Integer> getNumberOfPokemon() {
        return pokemonDao.getNumberOfPokemon();
    }

    @Override
    public void capture(Pokemon pokemon) {
        //When trying to write the database we must do it on a different thread than the one for the UI
        executor.execute(() -> pokemonDao.capture(pokemon));
    }

    private void refreshPokemon(int start, int end) {
        for (int idPokemon = start; idPokemon <= end; idPokemon++) {
            int finalIdPokemon = idPokemon;
            // running in a background thread
            // check if pokemon was fetched recently
            executor.execute(() -> {
                boolean pokemonExists = pokemonDao.hasPokemon(finalIdPokemon) > 0;
                if (!pokemonExists) {
                    // refresh the data
                    Pokemon response = pokemonService.getPokemon(finalIdPokemon);

                    if (response != null) {
                        // Update the database.The LiveData will automatically refresh so
                        // we don't need to do anything else here besides updating the database
                        pokemonDao.save(response);
                    }
                }
            });
        }
    }
}
