package edu.pokemon.iut.pokedex.architecture.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PokemonGestureListenerTest {

    private PokemonGestureListener pokemonGestureListener;

    @Mock
    private Context fakeContext;
    @Mock
    private GestureDetector fakeGestureDetector;
    @Mock
    private PokemonGestureListener.Listener fakeListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonGestureListener = spy(new PokemonGestureListener(fakeListener, fakeGestureDetector, fakeContext));
    }

    @Test
    public void initWithoutGestureDetector() {
        pokemonGestureListener = spy(new PokemonGestureListener(fakeListener, null, fakeContext));
        assertNotNull(pokemonGestureListener.getGestureDetector());
    }

    @Test
    public void onTouchTest() {
        //GIVEN
        MotionEvent motionEvent = mock(MotionEvent.class);
        View view = mock(View.class);

        //WHEN
        pokemonGestureListener.onTouch(view, motionEvent);

        //THEN
        verify(fakeGestureDetector).onTouchEvent(motionEvent);
        assertEquals(true, pokemonGestureListener.onTouch(view, motionEvent));
    }

    @Test
    public void onFlingTestSwipeUP() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(0f);
        when(e1.getY()).thenReturn(-120f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(0f);
        when(e2.getY()).thenReturn(131f);

        float velocityX = 0f;
        float velocityY = 201f;

        //WHEN
        pokemonGestureListener.onFling(e1, e2, velocityX, velocityY);

        //THEN
        verify(fakeListener).onSwipe(PokemonGestureListener.UP);
        assertEquals(true, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }

    @Test
    public void onFlingTestSwipeDOWN() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(0f);
        when(e1.getY()).thenReturn(120f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(0f);
        when(e2.getY()).thenReturn(-131f);

        float velocityX = 0f;
        float velocityY = 201f;

        //WHEN
        pokemonGestureListener.onFling(e1, e2, velocityX, velocityY);

        //THEN
        verify(fakeListener).onSwipe(PokemonGestureListener.DOWN);
        assertEquals(true, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }

    @Test
    public void onFlingTestSwipeLEFT() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(-120f);
        when(e1.getY()).thenReturn(0f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(131f);
        when(e2.getY()).thenReturn(0f);

        float velocityX = 201f;
        float velocityY = 0f;

        //WHEN
        pokemonGestureListener.onFling(e1, e2, velocityX, velocityY);

        //THEN
        verify(fakeListener).onSwipe(PokemonGestureListener.LEFT);
        assertEquals(true, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }

    @Test
    public void onFlingTestSwipeRIGHT() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(120f);
        when(e1.getY()).thenReturn(0f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(-131f);
        when(e2.getY()).thenReturn(0f);

        float velocityX = 201f;
        float velocityY = 0f;

        //WHEN
        pokemonGestureListener.onFling(e1, e2, velocityX, velocityY);

        //THEN
        verify(fakeListener).onSwipe(PokemonGestureListener.RIGHT);
        assertEquals(true, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }

    @Test
    public void onFlingTestSpeedNotEnoughHorizontalSwipe() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(120f);
        when(e1.getY()).thenReturn(0f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(-131f);
        when(e2.getY()).thenReturn(0f);

        float velocityX = 199f;
        float velocityY = 0f;

        //WHEN
        //THEN
        assertEquals(false, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }

    @Test
    public void onFlingTestSpeedNotEnoughVerticalSwipe() {
        //GIVEN
        MotionEvent e1 = mock(MotionEvent.class);
        when(e1.getX()).thenReturn(0f);
        when(e1.getY()).thenReturn(120f);
        MotionEvent e2 = mock(MotionEvent.class);
        when(e2.getX()).thenReturn(0f);
        when(e2.getY()).thenReturn(-131f);

        float velocityX = 0f;
        float velocityY = 199f;

        //WHEN
        //THEN
        assertEquals(false, pokemonGestureListener.onFling(e1, e2, velocityX, velocityY));
    }


}
