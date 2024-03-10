package org.example.Classes;

public class Episode {
    private String name;
    private String duration_time;

    public Episode(String name, String duration_time) {
        this.name = name;
        this.duration_time = duration_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration_time;
    }

    public String displayInfo() {
        StringBuilder display = new StringBuilder();
        display.append("Episode: ").append(name);
        if(duration_time!=null)
            display.append(" Duration: ").append(duration_time).append("\n");
        return display.toString();
    }
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("        {\n");
        json.append("          \"name\": \"").append(name).append("\",\n");
        if(duration_time!=null&&!duration_time.isEmpty())
            json.append("          \"duration\": \"").append(duration_time).append("\"\n");
        else
            json.deleteCharAt(json.length() - 2);
        json.append("        },\n");
        return json.toString();
    }



}
