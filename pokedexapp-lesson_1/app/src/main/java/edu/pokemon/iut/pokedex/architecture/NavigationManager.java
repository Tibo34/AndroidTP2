package edu.pokemon.iut.pokedex.architecture;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.List;

import javax.inject.Singleton;

import edu.pokemon.iut.pokedex.R;
import edu.pokemon.iut.pokedex.ui.pokemondetail.PokemonDetailFragment;
import edu.pokemon.iut.pokedex.ui.pokemonlist.PokemonListFragment;

/**
 * Helper class to ease the navigation between screens.
 */
@Singleton
public class NavigationManager {
    //Keys used to transition Views between Fragments
    public static final String IMAGE_VIEW_POKEMON_LOGO = "IMAGE_VIEW_POKEMON_LOGO";
    public static final String IMAGE_VIEW_POKEMON_CAPTURE = "IMAGE_VIEW_POKEMON_CAPTURE";
    public static final String IMAGE_VIEW_POKEMON_SHADOW = "IMAGE_VIEW_POKEMON_SHADOW";
    private static final String TAG_ROOT = "ROOT";
    //Allow to know if we the app is launch in a tablet or big screen allow
    private boolean tabletNavigation = false;
    //Allow to know if we navigate through the app with swipe
    private boolean isSwipe = false;

    private FragmentManager fragmentManager;
    private NavigationListener navigationListener;

    private int currentPokemon  = 0;

    /**
     * @return true if we are on Tablet/BigScreen, false otherwise
     */
    public boolean isTabletNavigation() {
        return tabletNavigation;
    }

    /**
     * @param tabletNavigation Set with true if the app is on Tablet/BigScreen, false otherwise
     */
    public void setTabletNavigation(boolean tabletNavigation) {
        this.tabletNavigation = tabletNavigation;
    }

    /**
     * Initialize the NavigationManager with a FragmentManager, which will be used at the
     * fragment transactions.
     *
     * @param fragmentManager provide by the activity
     */
    public void init(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.fragmentManager.addOnBackStackChangedListener(() -> {
            if (this.navigationListener != null) {
                this.navigationListener.onBackstackChanged();
            }
        });
    }

    /**
     * Displays the next fragment<br>
     * The parameter isRoot allow the NavigationManager to pop all the current backstack<br>
     *  @param fragment      which fragment we want to show
     * @param sharedElements any view that is shared between the current fragment and the new one
     * @param isRoot        true if the new fragment must considered as root of the application
     */
    private void open(Fragment fragment, List<View> sharedElements, boolean isRoot, @Nullable String tag) {
        if (this.fragmentManager != null) {
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.main_container, fragment).commit();
        }
    }

    /**
     * pops every fragment and starts the given fragment as a new one.
     *
     * @param sharedElements any view that is shared between the current fragment and the new one
     * @param fragment      which fragment we want to show
     */
    private void openAsRoot(Fragment fragment, List<View> sharedElements) {
        popEveryFragment();
        open(fragment, sharedElements, true, TAG_ROOT);
    }

    /**
     * Pops all the queued fragments
     */
    private void popEveryFragment() {
        // Clear all back stack.
        int backStackCount = this.fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id.
            int backStackId = this.fragmentManager.getBackStackEntryAt(i).getId();

            this.fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }

    /**
     * Navigates back by popping the back stack. If there is no more items left we finish the current activity.<br>
     * In case of swipe navigation we start de main fragment.
     *
     * @param baseActivity activity to finish in case of backstack empty
     */
    public void navigateBack(Activity baseActivity) {
        if (isSwipe) {
            isSwipe = false;
            fragmentManager.popBackStack(TAG_ROOT, 0);
            //startPokemonList(null, null);
        } else {
            if (this.fragmentManager.getBackStackEntryCount() == 0) {
                // we can finish the base activity since we have no other fragments
                baseActivity.finish();
            } else {
                this.fragmentManager.popBackStackImmediate();
            }
        }
    }


    /**
     * Start the pokemon list fragment.<br>
     * We can pass it a View as a shared element between fragments.<br>
     * Also a query can be used to filter the list shown<br>
     * @param sharedElement {@link View} shared between both fragments
     * @param query         {@link CharSequence} that filter the list
     */
    public void startPokemonList(List<View> sharedElement, CharSequence query) {
        Fragment fragment = PokemonListFragment.newInstance(query);
        openAsRoot(fragment, sharedElement);
    }

    /**
     * Start the pokemon detail view for the pokemonId<br>
     * We can pass it a View as a shared element between fragments.<br>
     *  @param pokemonId     the pokemon id to show
     * @param isSwipe       true if we swipe to show the new pokemon, false otherwise
     */
    public void startPokemonDetail(int pokemonId, boolean isSwipe) {
        // TODO 21) ENREGISTRER isSwipe dans this.isSwipe
        this.isSwipe=isSwipe;
        // TODO 22) INSTANCIER UN PokemonDetailFragment VIA newInstance
        PokemonDetailFragment pokemonDetailFragment=PokemonDetailFragment.newInstance( pokemonId );
        // TODO 23) APPELER open AVEC LE FRAGMENT, IL N'Y A PAS DE sharedElement, CE N'EST PAS UNE VUE ROOT, IL N'Y A PAS DE TAG
        open( pokemonDetailFragment,null,false,null );

    }

    /**
     * @return true if the current fragment displayed is a root fragment
     */
    public boolean isRootFragmentVisible() {
        return this.fragmentManager.getBackStackEntryCount() <= 1;
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public int getCurrentPokemon() {
        return currentPokemon;
    }

    /**
     * Listener interface for navigation events.
     */
    public interface NavigationListener {

        /**
         * Callback on backstack changed.
         */
        void onBackstackChanged();
    }
}
