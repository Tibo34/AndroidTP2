package edu.pokemon.iut.pokedex.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Pokemon {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("base_experience")
    @Expose
    private int baseExperience;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("is_default")
    @Expose
    private boolean isDefault;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("sprites")
    @Expose
    @Ignore
    private Sprites sprites;
    @SerializedName("types")
    @Expose
    @TypeConverters(PokemonTypeStringTypeConverters.class)
    private List<Type> types;

    private String spritesString;

    private boolean isCapture = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public String getSpritesString() {
        return spritesString;
    }

    public void setSpritesString(String spritesString) {
        this.spritesString = spritesString;
    }

    public String getStringId() {
        return Integer.toString(this.id);
    }

    public String getStringBaseExp() {
        return Integer.toString(this.baseExperience);
    }

    public String getStringHeight() {
        return Float.toString((float) this.height / 10);
    }

    public String getStringWeight() {
        return Float.toString((float) this.weight / 10);
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public boolean isCapture() {
        return isCapture;
    }

    @SuppressWarnings("WeakerAccess")
    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public Pokemon capture() {
        setCapture(!isCapture);
        return this;
    }
}
