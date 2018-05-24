package models;

import play.db.jpa.Model;
import javax.persistence.Entity;



@Entity
public class Trainer extends Model {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Trainer(String firstName, String lastName,String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static Trainer findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }


}
