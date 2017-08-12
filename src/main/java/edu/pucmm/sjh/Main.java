package edu.pucmm.sjh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static spark.Spark.*;

/**
 * Created by vacax on 03/07/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Class.forName("org.postgresql.Driver").newInstance();
        
        //indicando el puerto de Spark,
        port(getPuertoHeroku());

        //inicializando
        get("/", (request, response) -> {
            return "Hola Mundo Heroku - Laptop Camacho";
        });

        get("/creartabla", (request, response) -> {            
            
            Connection connection = DriverManager.getConnection(getUrlBaseDatos());
            
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

            //ArrayList<String> output = new ArrayList<String>();
            String salida = "";
            while (rs.next()) {
                //output.add("Read from DB: " + rs.getTimestamp("tick"));
                salida+="Read from DB: " + rs.getTimestamp("tick")+"<br/>";
            }
            
            connection.close();

            return salida;
        });
    }

    static int getPuertoHeroku() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //En caso de no pasar la información, toma el puerto 4567
    }
    
     static String getUrlBaseDatos() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("JDBC_DATABASE_URL") != null) {
            return processBuilder.environment().get("JDBC_DATABASE_URL");
        }
        return ""; //En caso de no pasar la información, toma el puerto 4567
    }
}
