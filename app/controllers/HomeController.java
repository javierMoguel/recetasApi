package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Rating;
import models.Recipes;
import models.Steps;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    @Inject
    FormFactory formFactory;

    public Result getAllRecipes() {

        return ok( "Todas las recetas" );
    }

    public Result getRecipe( String query ) {

        return ok( "Una receta" );
    }

    public Result createRecipe( Http.Request request ) {

        JsonNode bodyRequest = request.body().asJson();

        if (bodyRequest == null){
            return Results.status(500);
        }

        Form<Recipes> recipeForm = formFactory.form(Recipes.class).bindFromRequest(request);
        if( recipeForm.hasErrors()) {
            return Results.notAcceptable(recipeForm.errorsAsJson());
        }

        Recipes recipe = recipeForm.get();
        Steps step = new Steps();
        step.setPasos( bodyRequest.get("pasos").get("pasos").asText() );
        recipe.setPasos(step);

        //if ( bodyRequest.get("ratings").get("rating") != null ){
            Rating rating = new Rating();
            rating.setRating(bodyRequest.get("ratings").get("rating").asText());
            recipe.addRating(rating);
        //}

        recipe.save();

        JsonNode node = Json.toJson(recipe);
        return ok("Creada la receta " + node);
    }

    public Result updateRecipe( String id, Http.Request request ) {

        return ok( "Actualizar" );
    }

    public Result deleteRecipe( String id ) {

        return ok( "Eliminar" );
    }


}
