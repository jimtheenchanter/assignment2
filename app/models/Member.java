package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member extends Model
{
  public String firstName;
  public String lastName;
  public String address;
  public String email;
  public String password;
  public float height;
  public float startWeight;
  public String gender;


  @OneToMany(cascade = CascadeType.ALL)
  public List<Assessment> assessments = new ArrayList<Assessment>();

  /** Member constructor which stores the following parameters
   *
   * @param firstName
   * @param lastName
   * @param address
   * @param email
   * @param password
   * @param height
   * @param startWeight
   * @param gender
   */
  public Member(String firstName, String lastName, String address, String email, String password, float height, float startWeight, String gender)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.email = email;
    this.password = password;
    this.height = height;
    this.startWeight = startWeight;
    this.gender = gender;
  }

  //for verifying member by password
  public static Member findByEmail(String email)
  {
    return find("email", email).first();
  }

  /**
   *
   * @param password
   * @return true or false for password match
   */
  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }

  public float getHeight()
  {
    return height;
  }

  /**
   * constructor for latest assessment creating a new assessment if none exists
   * @param m member fields
   * @return a assessment
   */

  public static Assessment latestAssessment(Member m){
    Assessment a;
    if(m.assessments.size() == 0){
      a = new Assessment( m.startWeight, 0, 0,0, 0,0 , false );
    }else{
      a =  m.assessments.get(m.assessments.size()-1);
    }
    return a;
  }

  /**
   * This method gets the gender of the member
   * @return
   */
  public String getGender()
  {
    return gender;
  }

  public String getFirstName()
{
  return firstName;
}


// setter to capitalise 1st letter of firstname
  public void setFirstName()
  {
    firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
  }

  public String getLastName()
  {
    return lastName;
  }


  //method to capitalise members initial letter in last name
  public void setLastName()
  {
    lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1);
  }
}
