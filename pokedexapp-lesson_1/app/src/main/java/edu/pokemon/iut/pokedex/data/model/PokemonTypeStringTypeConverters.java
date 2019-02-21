package edu.pokemon.iut.pokedex.data.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

/**
 * {@link TypeConverter} are used to convert some complex object in a way that {@link android.arch.persistence.room.RoomDatabase} can store.<br>
 * This class allow to convert Any complex {@link List<Object>} in Json to store in the database, and to convert them back to object when reading it.
 */
@SuppressWarnings("WeakerAccess")
public class PokemonTypeStringTypeConverters {

    private static final Gson gson = new Gson();

    /**
     * Convert from Json {@link String} to {@link List<Object>}
     *
     * @param data json strong to convert
     * @return {@link List<Object>}
     */
    @TypeConverter
    public static List<Type> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        java.lang.reflect.Type listType = new TypeToken<List<Type>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    /**
     * Convert from {@link List<Object>} to Json {@link String}
     *
     * @param someObjects {@link List<Object>} to convert
     * @return json as {@link String}
     */
    @TypeConverter
    public static String someObjectListToString(List<Type> someObjects) {
        return gson.toJson(someObjects);
    }
}