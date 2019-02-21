package edu.pokemon.iut.pokedex.architecture.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.pokemon.iut.pokedex.architecture.NavigationManager;

/**
 * Dependencies injection Module for Navigation
 */
@SuppressWarnings("WeakerAccess")
@Module
public class NavigationModule {

    /**
     * Give access to the navigation manager that provide methods to navigate through the app
     *
     * @return an instance of {@link NavigationManager}
     */
    @Provides
    @Singleton
    public NavigationManager providesNavigationManager() {
        return new NavigationManager();
    }
}
