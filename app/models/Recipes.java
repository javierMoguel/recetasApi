package models;

import io.ebean.Ebean;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Pattern;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Recipes extends Model{

    public static final Finder<Long, Recipes> find = new Finder<>(Recipes.class);

    @Id
    private Long id;

    @Version
    private Long version;

    @WhenCreated
    private Timestamp whenCreated;

    @WhenModified
    private Timestamp whenModified;

    @Required
    @MaxLength(value = 40, message = "El tamaño máximo son 40 caracteres")
    private String name;

    @Required
    @Valid
    @Pattern("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")
    private String time;

    @Required
    @Valid
    private String category;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @Required
    @Valid
    private Steps pasos;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "parentRecipe")
    @JsonManagedReference
    @Required
    @Valid
    private List<Rating> ratings = new ArrayList<>();

    @JsonManagedReference
    @ManyToMany( cascade = CascadeType.ALL )
    @Required
    @Valid
    public List<Ingredients> ingredients = new ArrayList<>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Steps getPasos() {
        return pasos;
    }

    public void setPasos(Steps pasos) {
        pasos.setParentRecipe(this);
        this.pasos = pasos;
    }

    public void addRating( Rating rating) {
        this.ratings.add(rating);
        rating.setParentRecipe(this);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals (Object obj) {
        if( obj == null ) {
            return false;
        }
        if( obj instanceof Recipes) {
            Recipes objRecipe = (Recipes) obj;
            if( objRecipe.getName() == null) {
                return false;
            } else {
                return objRecipe.getName().equals( this.getName());
            }
        } else {
            return false;
        }
    }

    public void checkRecipe() {
        List<Ingredients> i = this.ingredients;
        Ingredients ing;
        for (int j = 0; j < i.size(); j++) {

            i.get(j).setIngredient(i.get(j).getIngredient());
            i.get(j).setCantidad(i.get(j).getCantidad().toLowerCase());
            ing = Ingredients.findIngredientByNameAndUnit(i.get(j).getIngredient(), i.get(j).getCantidad());
            if (ing != null) {
                i.set(j, ing);
            }
        }
        this.save();
    }

    public static Recipes findRecipeById( Long id ) {
        return find.byId(id);
    }


}
