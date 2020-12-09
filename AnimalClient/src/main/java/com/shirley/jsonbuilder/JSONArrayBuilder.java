package com.shirley.jsonbuilder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;


/**
 * Inspiration derived from a book
 * I've been reading
 * 
 * @author Chantal Shirley
 */
public class JSONArrayBuilder {
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    
    // Adding id value pair
    public String JSONGenerator(String id) {
        String jsonArray = null;
        
        jsonArrayBuilder.add(
                Json.createObjectBuilder()
                .add("petId", id)
        );
        
        // Build array
        JsonArray petId = jsonArrayBuilder.build();
        
        jsonArray =  petId.toString();
        
        return jsonArray;
    }
}
