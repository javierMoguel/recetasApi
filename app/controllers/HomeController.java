package controllers;

import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public Result getAllRecipes() {

        return ok( "Todas las recetas" );
    }

    public Result getRecipe( String query ) {

        return ok( "Una receta" );
    }

    public Result createRecipe( Http.Request request ) {

        return ok( "Crear receta" );
    }

    public Result updateRecipe( String id, Http.Request request ) {

        return ok( "Actualizar" );
    }

    public Result deleteRecipe( String id ) {

        return ok( "Eliminar" );
    }


}
