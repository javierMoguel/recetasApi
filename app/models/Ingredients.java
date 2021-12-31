package models;

import play.data.validation.Constraints.Required;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Required
    String ingredient;

    @Required
    String cantidad;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

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

    public static Ingredients findIngredientByNameAndUnit(String name, String units) {
        return find.query().where().isNotNull("ingredient").eq("ingredient", name)
                .and().isNotNull("cantidad").eq("cantidad", units).findOne();
    }
}
