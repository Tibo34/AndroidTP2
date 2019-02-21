package edu.pokemon.iut.pokedex.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;

import edu.pokemon.iut.pokedex.data.dao.PokemonDao;
import edu.pokemon.iut.pokedex.data.model.Pokemon;
import edu.pokemon.iut.pokedex.data.service.PokemonService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PokemonRepositoryTest {

    private final Executor fakeExecutor = Runnable::run;
    private PokemonRepository pokemonRepository;
    @Mock
    private PokemonService fakePokemonService;
    @Mock
    private PokemonDao fakePokemonDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonRepository = spy(new PokemonRepositoryImpl(fakePokemonService, fakePokemonDao, fakeExecutor));
    }

    @Test
    public void getPokemonsTest_withNoFilter_andPokemonExist() {
        //GIVEN that pokemon already exists we don't refresh them
        when(fakePokemonDao.hasPokemon(anyInt())).thenReturn(1);
        LiveData<List<Pokemon>> liveDateListPokemon = new MutableLiveData<>();
        when(fakePokemonDao.loadAll()).thenReturn(liveDateListPokemon);

        //WHEN asking all pokemons without query
        pokemonRepository.getPokemons(null);

        //THEN call dao for All pokemon and return a LiveData of list of pokemons
        verify(fakePokemonDao).loadAll();
        assertEquals(pokemonRepository.getPokemons(null), liveDateListPokemon);

    }

    @Test
    public void getPokemonsTest_withNoFilter_andPokemonDoesNotExist() {
        //GIVEN that pokemon already exists we don't refresh them
        //And we ask for one pokemon only
        pokemonRepository.setMaxIdPokemon(1);
        Pokemon pokemon = mock(Pokemon.class);
        when(fakePokemonService.getPokemon(anyInt())).thenReturn(pokemon);
        when(fakePokemonDao.hasPokemon(anyInt())).thenReturn(0);

        //WHEN asking all pokemons without query
        pokemonRepository.getPokemons(null);

        //THEN call dao for All pokemon and return a LiveData of list of pokemons
        //and call service to get pokemon and save them with DAO when it exist
        verify(fakePokemonDao).loadAll();
        verify(fakePokemonService).getPokemon(anyInt());
        verify(fakePokemonDao).save(pokemon);
        assertEquals(pokemonRepository.getPokemons(null), fakePokemonDao.loadAll());
    }

    @Test
    public void getPokemonsTest_withFilter_andPokemonExist() {
        //GIVEN
        CharSequence query = "Test";
        LiveData<List<Pokemon>> liveDateListPokemon = new MutableLiveData<>();
        when(fakePokemonDao.loadAllWithFilter(query.toString() + "%")).thenReturn(liveDateListPokemon);

        //WHEN asking all pokemons without query
        pokemonRepository.getPokemons(query);

        //THEN call dao for All pokemon and return a LiveData of list of pokemons
        //and call service to get pokemon and save them with DAO when it exist
        verify(fakePokemonDao).loadAllWithFilter(query.toString() + "%");
        assertEquals(pokemonRepository.getPokemons(query), liveDateListPokemon);
    }

    @Test
    public void getPokemonTest_andPokemonDoesNotExist() {
        //GIVEN
        int pokemonId = 1;
        Pokemon pokemon = mock(Pokemon.class);
        LiveData<Pokemon> liveData = new MutableLiveData<>();

        when(fakePokemonDao.load(pokemonId)).thenReturn(liveData);
        when(fakePokemonService.getPokemon(pokemonId)).thenReturn(pokemon);
        when(fakePokemonDao.hasPokemon(anyInt())).thenReturn(0);

        //WHEN asking for the pokemon
        pokemonRepository.getPokemon(pokemonId);

        //THEN call dao for this pokemon and return a LiveData of this pokemon
        //and call service to get pokemon and save him with DAO when it exist
        verify(fakePokemonDao).load(pokemonId);
        verify(fakePokemonService).getPokemon(pokemonId);
        verify(fakePokemonDao).save(pokemon);
        assertEquals(pokemonRepository.getPokemon(pokemonId), liveData);
    }

    @Test
    public void capture_pokemonNotNull() {
        //GIVEN
        Pokemon pokemon = mock(Pokemon.class);
        //WHEN
        pokemonRepository.capture(pokemon);
        //THEN
        verify(fakePokemonDao).capture(pokemon);
    }

    @Test
    public void capture_pokemonNull() {
        //GIVEN
        //WHEN
        pokemonRepository.capture(null);
        //THEN
        verify(fakePokemonDao).capture(null);
    }

    @Test
    public void getNumbersOfPokemonTest() {
        //GIVEN
        LiveData<Integer> liveData = new MutableLiveData<>();
        when(fakePokemonDao.getNumberOfPokemon()).thenReturn(liveData);

        //WHEN
        pokemonRepository.getNumberOfPokemon();

        //THEN
        verify(fakePokemonDao).getNumberOfPokemon();
        assertEquals(pokemonRepository.getNumberOfPokemon(), liveData);
    }
}
