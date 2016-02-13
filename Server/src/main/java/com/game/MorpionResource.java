package com.game;

/**
 * Created by Julien Moulin on 13/02/2016.
 */

import com.google.gson.*;
import model.Case;
import model.Morpion;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/morpion")
public class MorpionResource {

    /**
     * Receives a grid and returns the same grid after the turn of AI + if the party is over and who wins
     * (0 : null, 1 Human, -1 : IA)
     * @param data JSONObject (example : {grille : [0,0,0,0,0,0,0,0,0]})
     * @return Response JSON
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response game(String data){
        JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
        JsonArray jsona = jsonObject.get("grille").getAsJsonArray();

        Case[][] cases = new Case[3][3];
        int nombreCasesJouees = 0;
        int nombreCasesCrees = 0;
        for(int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int joueur = jsona.get(nombreCasesCrees).getAsInt();
                if(joueur != 0){ nombreCasesJouees++; }
                cases[x][y] = new Case(joueur);
                nombreCasesCrees++;
            }
        }

        Morpion morpion = new Morpion(cases, nombreCasesJouees);
        if(!morpion.partieFinie()){
            morpion.IA();
        }

        String json = new Gson().toJson(morpion.toString());
        return Response.status(Response.Status.OK).entity(json).build();
    }

}
