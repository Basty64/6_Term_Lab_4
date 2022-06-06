package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static ArrayList<Reactor> JSON(String s) throws FileNotFoundException, IOException, ParseException {
        ArrayList<Reactor> reactorsList = new ArrayList<>();

        FileReader reader = new FileReader(s);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);


        for (Object obj : jsonArray) {
            JSONObject pars = (JSONObject) obj;
            reactorsList.add(new Reactor((String) pars.get("classs"), (Double) pars.get("burnup"), (Double) pars.get("kpd"),
                    (Double) pars.get("enrichment"), (Double) pars.get("termal_capacity"), (Double) pars.get("electrical_capacity"),
                    (Double) pars.get("life_time"), (Double) pars.get("first_load"),"JSON"));
        }

        return reactorsList;
    }


}
