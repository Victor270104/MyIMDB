package org.example.Classes;


public class Credentials {
    private String email;
    private String password;
    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String displayInfo() {
        return "Email: " +"\u001B[1m"+ email +"\u001B[0m"+ "\n" +
                "Password: "+"\u001B[1m" + password +"\u001B[0m";
    }
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("        \"email\": "+"\""+email+"\""+",\n");
        json.append("        \"password\": "+"\""+password+"\""+"\n");
        return json.toString();
    }

}
