package com.example.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class User {
    
    private final String filePath = "java/httpExample/demo/data/record.json";

    public JSONArray getAllUsers(){
        
        JSONParser parser = new JSONParser();
        try {
            JSONArray arr = (JSONArray) parser.parse(new FileReader(filePath));
            List<Object> result = (List<Object>) arr.stream().sorted(new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    // TODO Auto-generated method stub
                    JSONObject json1 = (JSONObject) o1;
                    JSONObject json2 = (JSONObject) o2;
                    return json1.get("tag").toString().compareTo(json2.get("tag").toString());
                }
            }).collect(Collectors.toList());
            
            JSONArray newArray = new JSONArray();
            result.stream().forEach(obj -> {newArray.add(obj);});

            return newArray;
        } catch (ParseException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return null;
    }
    
    
    public static void main(String[] args) {
        User user = new User();
        System.out.println(user.getAllUsers());
    }
    
}
