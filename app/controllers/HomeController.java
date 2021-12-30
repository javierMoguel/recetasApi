package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Rating;
import models.Recipes;
import models.Steps;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import play.twirl.api.Content;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    @Inject
    FormFactory formFactory;

    public Result getAllRecipes( Http.Request request ) {

        List<Recipes> totalRecetas = Recipes.find.all( );

        if( request.accepts("application/xml")){
            Content content = views.xml.recipes.render(totalRecetas);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(totalRecetas);
            return ok(node);
        } else {
            return ok("bad request");
        }

    }

    public Result getRecipe( Http.Request request, String query ) {

        Recipes singleReceta = Recipes.findRecipeById( Long.valueOf(query) );

        if( request.accepts("application/xml")){
            Content content = views.xml.recipe.render(singleReceta);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(singleReceta);

            return ok(node);
        } else {
            return ok("bad request");
        }

    }

    public Result createRecipe( Http.Request request ) {

        Form<Recipes> recipeForm = formFactory.form(Recipes.class).bindFromRequest(request);
        if( recipeForm.hasErrors()) {
            return Results.notAcceptable(recipeForm.errorsAsJson());
        }

        Recipes recipe = recipeForm.get();
        System.out.println( recipeForm.field("ratings").value() );

        recipe.save();

        if( request.accepts("application/xml")){
            Content content = views.xml.recipe.render(recipe);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(recipe);
            return ok(node);
        } else {
            return ok("bad request");
        }

    }

    public Result updateRecipe( String id, Http.Request request ) {

        return ok( "Actualizar" );
    }

    public Result deleteRecipe( String id ) {
        Recipes singleReceta = Recipes.findRecipeById( Long.valueOf(id) );

        if ( singleReceta == null ) {
            return Results.notFound("Esta receta no existe");
        }

        String name = singleReceta.getName();
        singleReceta.delete();

        return ok("Receta " + name + " eliminada");
    }

}
