package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Ingredients extends Model{

    public static final Finder<Long, Ingredients> find = new Finder<>(Ingredients.class);

    @Id
    private Long id;

    @Version
    private Long version;

    @WhenCreated
    private Timestamp whenCreated;

    @WhenModified
    private Timestamp whenModified;

    @JsonBackReference
    @ManyToMany( mappedBy = "ingredients" )
    public List<Recipes> recipes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Timestamp getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Timestamp whenModified) {
        this.whenModified = whenModified;
    }

    public List<Recipes> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipes> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals (Object obj) {
        if( obj == null ) {
            return false;
        }
        if( obj instanceof Ingredients) {
            Ingredients objRecipe = (Ingredients) obj;
            if( objRecipe.getRecipes() == null) {
                return false;
            } else {
                return objRecipe.getRecipes().equals( this.getRecipes());
            }
        } else {
            return false;
        }
    }

    public static Ingredients findRecipeById(Long id ) {
        return find.byId(id);
    }


}
