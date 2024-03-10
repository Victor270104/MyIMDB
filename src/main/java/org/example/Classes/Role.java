package org.example.Classes;

public class Role {
    private String production;
    private String type;

    public Role(String production, String type) {
        this.production = production;
        this.type = type;
    }

    public String toJson(){
        StringBuilder json = new StringBuilder();
        json.append("    {\n");
        json.append("      \"production\": \"").append(production).append("\",\n");
        json.append("      \"type\": \"").append(type).append("\"\n");
        json.append("    },\n");
        return json.toString();
    }

    public String displayInfo(){
        return "Prod: " + production +
                ", Type: " + type;
    }

    public String getProduction() {
        return production;
    }

    public String getType() {
        return type;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public void setType(String type) {
        this.type = type;
    }
}
