package models;

/**trainer mddel which holds trainers details and
 * methods for verifying trainer by email and password
 */

import play.db.jpa.Model;
import javax.persistence.Entity;



@Entity
public class Trainer extends Model {
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    /**
     * Constructor for trainer
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
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
