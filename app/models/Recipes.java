package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import javax.persistence.*;
import java.sql.Timestamp;

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

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Steps pasos;

    public Steps getPasos() {
        return pasos;
    }

    public void setPasos(Steps pasos) {
        pasos.setParentRecipe(this);
        this.pasos = pasos;
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

    public static Recipes findRecipeById( Long id ) {
        return find.byId(id);
    }


}
