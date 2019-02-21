package edu.pokemon.iut.pokedex.architecture.listener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom implementation of {@link GestureDetector.SimpleOnGestureListener} and {@link View.OnTouchListener}<br>
 * This GestureListener allow any view through the setOnTouchListener to receive an event of Fling(Swipe) on it.<br>
 * Call is {@link PokemonGestureListener.Listener} with this values : <br>
 * {@value #UP} Swipe from Bottom to Top<br>
 * {@value #DOWN} Swipe from Top to Bottom<br>
 * {@value #LEFT} Swipe from Right to Left<br>
 * {@value #RIGHT} Swipe from Left to Right<br>
 */
@SuppressWarnings("WeakerAccess")
public class PokemonGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    //The for direction detected
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private static final String TAG = PokemonGestureListener.class.getSimpleName();
    //The values of each velocity limit to consider it's a swipe
    private static final float SWIPE_MAX_OFF_PATH = 250;
    private static final float SWIPE_THRESHOLD_VELOCITY = 200;
    private static final float SWIPE_MIN_DISTANCE = 120;

    //The interface that allow the caller to have a callback of the swipe
    private final Listener listener;

    private final GestureDetector gestureDetector;

    /**
     * Constructor for the {@link GestureDetector.SimpleOnGestureListener}
     *
     * @param listener        callBack interface {@link PokemonGestureListener.Listener}
     * @param gestureDetector custom {@link GestureDetector}, if not given a new instance will be create
     * @param context         the context needed for instantiate a new {@link GestureDetector}
     */
    public PokemonGestureListener(Listener listener, GestureDetector gestureDetector, Context context) {
        super();
        this.listener = listener;
        if (gestureDetector == null) {
            this.gestureDetector = new GestureDetector(context, this);
        } else {
            this.gestureDetector = gestureDetector;
        }
    }

    /**
     * Override of onTouch to detect the swipe. <br>
     * We don't call performClick for accessibility purpose, because the swipe will not be take in account in such case
     *
     * @param view        {@link View} that's touch
     * @param motionEvent {@link MotionEvent} that's triggered
     * @return true if we propagate the event, false if not
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //We first test if the swipe is short enough to take account of it and in TOP BOTTOM Direction
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
            //Then we test if the swipe is fast enough and not with some LEFT RIGHT Direction
            if (Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY || Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }

            //If the distance is enough we trigger the callBack
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                listener.onSwipe(DOWN);
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                listener.onSwipe(UP);
            }
        } else {
            //Then we test if the swipe is fast enough on the other direction
            if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                return false;
            }

            //If the distance is enough we trigger the callBack
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                listener.onSwipe(RIGHT);
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                listener.onSwipe(LEFT);
            }
        }
        return true;
    }

    /**
     * Inner interface for allow callBack from the listener
     */
    public interface Listener {
        /**
         * Called when an onFling(Swipe) is detected.
         * Return one of this values :
         * {@value #UP} Swipe from Bottom to Top<br>
         * {@value #DOWN} Swipe from Top to Bottom<br>
         * {@value #LEFT} Swipe from Right to Left<br>
         * {@value #RIGHT} Swipe from Left to Right<br>
         *
         * @param direction an int from the constant.
         */
        void onSwipe(int direction);
    }

    /**
     * Getter for testing purpose only
     * @return {@link GestureDetector}
     */
    @VisibleForTesting
    protected GestureDetector getGestureDetector(){
        return gestureDetector;
    }
}
