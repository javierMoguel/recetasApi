package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ingredients;
import models.Recipes;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
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
    MessagesApi messagesApi;

    @Inject
    FormFactory formFactory;


    public Result getAllRecipes( Http.Request request ) {
        Messages messages = messagesApi.preferred(request);
        List<Recipes> totalRecetas = Recipes.find.all( );

        if ( totalRecetas == null ) {
            return Results.notFound(messages.at("recipesDoesntExist"));
        }

        if( request.accepts("application/xml")){
            Content content = views.xml.recipes.render(totalRecetas);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(totalRecetas);
            return ok(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }

    }

    public Result getRecipeById( Http.Request request, String id ) {
        Messages messages = messagesApi.preferred(request);

        Recipes singleReceta = Recipes.findRecipeById( Long.valueOf(id) );
        if ( singleReceta == null ) {
            return Results.notFound(messages.at("recipeDoesntExist"));
        }
        if( request.accepts("application/xml")){
            Content content = views.xml.recipe.render(singleReceta);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(singleReceta);

            return ok(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }

    }

    public Result createRecipe( Http.Request request ) {
        Messages messages = messagesApi.preferred(request);

        Form<Recipes> recipeForm = formFactory.form(Recipes.class).bindFromRequest(request);
        if( recipeForm.hasErrors()) {
            return Results.notAcceptable(recipeForm.errorsAsJson());
        }

        Recipes recipe = recipeForm.get();
        recipe.checkRecipe();

        if( request.accepts("application/xml")){
            Content content = views.xml.recipe.render(recipe);
            return Results.created(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(recipe);
            return Results.created(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }

    }

    public Result updateRecipe( String id, Http.Request request ) {
        Messages messages = messagesApi.preferred(request);

        Recipes singleReceta = Recipes.findRecipeById( Long.valueOf(id) );
        if ( singleReceta == null ) {
            return Results.notFound(messages.at("recipeDoesntExist"));
        }
        Form<Recipes> recipeForm = formFactory.form(Recipes.class).bindFromRequest(request);

        if( recipeForm.hasErrors()) {
            return Results.notAcceptable(recipeForm.errorsAsJson());
        }

        if (recipeForm.get().getName() != null ) {
            singleReceta.setName( recipeForm.get().getName() );
        }
        if (recipeForm.get().getTime() != null ) {
            singleReceta.setTime( recipeForm.get().getTime() );
        }
        if (recipeForm.get().getCategory() != null ) {
            singleReceta.setCategory( recipeForm.get().getCategory() );
        }
        if (recipeForm.get().getRatings() != null ) {
            singleReceta.setRatings( recipeForm.get().getRatings() );
        }
        if (recipeForm.get().getIngredients() != null ) {
            singleReceta.setIngredients( recipeForm.get().getIngredients() );
        }
        if (recipeForm.get().getPasos() != null ) {
            singleReceta.setPasos( recipeForm.get().getPasos() );
        }
        singleReceta.update();

        if( request.accepts("application/xml")){
            Content content = views.xml.recipe.render(singleReceta);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(singleReceta);
            return ok(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }

    }

    public Result deleteRecipe( Http.Request request, String id ) {
        Messages messages = messagesApi.preferred(request);

        Recipes singleReceta = Recipes.findRecipeById( Long.valueOf(id) );

        if ( singleReceta == null ) {
            return Results.notFound(messages.at("recipeDoesntExist"));
        }

        String name = singleReceta.getName();
        singleReceta.delete();

        return ok(messages.at("recipe") + " " + name + " " + messages.at("deleted"));
    }

    public Result searchByIng( Http.Request request, String query) {
        Messages messages = messagesApi.preferred(request);

        Ingredients ingredients = Ingredients.findByName( query );
        if ( ingredients == null ) {
            return Results.notFound(messages.at("ingredientDoesntExist"));
        }
        List<Recipes> recipes = ingredients.getRecipes();

        if( request.accepts("application/xml")){
            Content content = views.xml.recipes.render(recipes);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(recipes);
            return ok(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }
    }

    public Result searchByCat( Http.Request request, String query ) {
        Messages messages = messagesApi.preferred(request);

        List<Recipes> recipes = Recipes.findByCategory( query );
        if ( recipes == null ) {
            return Results.notFound(messages.at("categoryDoesntExist"));
        }
        if( request.accepts("application/xml")){
            Content content = views.xml.recipes.render(recipes);
            return ok(content);
        } else if ( request.accepts("application/json")) {
            JsonNode node = Json.toJson(recipes);
            return ok(node);
        } else {
            return Results.badRequest(messages.at("badRequest"));
        }
    }
}
