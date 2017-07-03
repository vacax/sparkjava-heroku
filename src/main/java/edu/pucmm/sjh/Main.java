package edu.pucmm.sjh;

import static spark.Spark.*;

/**
 * Created by vacax on 03/07/17.
 */
public class Main {

    public static void main(String[] args) {

        //indicando el puerto de Spark,
        port(getPuertoHeroku());

        //inicializando
        get("/", (request, response) -> {
            return "Hola Mundo Heroku";
        });
    }

    static int getPuertoHeroku() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //En caso de no pasar la información, toma el puerto 4567
    }
}
