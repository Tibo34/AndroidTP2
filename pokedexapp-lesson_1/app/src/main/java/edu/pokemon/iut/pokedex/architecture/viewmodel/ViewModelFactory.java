package edu.pokemon.iut.pokedex.architecture.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Factory for ViewModels
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels;

    /**
     * Receive a map of ViewModel provided by dagger with the intoMap annotation and ViewModelKey annotations
     *
     * @param viewModels list of viewModel to create
     */
    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels) {
        this.viewModels = viewModels;
    }

    /**
     * The method create is override to allow ViewModel creation with non-empty constructor.<br>
     * The arguments of the constructor of ViewModel must be injectable by dagger.
     *
     * @param modelClass the instance of ViewModel
     * @param <T>        the class wich implements the ViewModel interface
     * @return an instance of the actual ViewModel
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<ViewModel> viewModelProvider = viewModels.get(modelClass);

        if (viewModelProvider == null) {
            throw new IllegalArgumentException("model class " + modelClass + " not found");
        }

        //noinspection unchecked
        return (T) viewModelProvider.get();
    }
}