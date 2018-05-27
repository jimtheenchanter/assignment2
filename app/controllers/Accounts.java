package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{
  public static void signup()
  {
    render("signup.html");
  }

  public static void login()
  {
    render("login.html");
  }

  public static void register(String firstName, String lastName, String address, String email, String password, float height, float startWeight, String gender)
  {
    Logger.info("Registering new user " + email);
    Member member = new Member(firstName, lastName, address, email, password, height, startWeight, gender);
    member.save();
    redirect("/");
  }


  /**
   * method to authenticate user whether trainer or member
   * @param email
   * @param password
   */
  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);
    if(Member.findByEmail(email) != null) {
      Member member = Member.findByEmail(email);
      if ((member != null) && (member.checkPassword(password))) {
        Logger.info("Authentication successful");
        session.put("logged_in_Memberid", member.id);
        redirect("/dashboard");
      } else {
        Logger.info("Authentication failed");
        redirect("/login");
      }

          }else if(Trainer.findByEmail(email) != null) {
        Trainer trainer = Trainer.findByEmail(email);
        if ((trainer != null) && (trainer.checkPassword(password))) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect("/trainer");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    } else {
        Logger.info("Authentication failed");
        redirect("/login");
    }
  }

  /**
   * log out method
   */
  public static void logout()
  {
    session.clear();
    redirect ("/");
  }

  //find logged in member by database ID
  public static Member getLoggedInMember()
  {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    } else {
      login();
    }
    return member;
  }

  /**
   * method for member to update their details - any blanks are set to previous
   * @param firstName
   * @param lastName
   * @param email
   * @param password
   * @param height
   * @param startWeight
   * @param gender
   */

public static void editUser(String firstName, String lastName, String email, String password, String height, String startWeight, String gender)
{

  Member member = Accounts.getLoggedInMember();
/**
 * if any of the fields are left blank it should fill with original details insterad of throwing an error
 */
  if(firstName.equals("")){
    member.firstName = member.firstName;
  }else{
    member.firstName = firstName;
  }

  if(lastName.equals("")){
    member.lastName = member.lastName;
  }else{
    member.lastName = lastName;
  }

  if(email.equals("")) {
    member.email = member.email;
  }else{
    member.email = email;
  }

  if (height.equals("")) {
    member.height = member.height;
  }else{
    member.height = Float.parseFloat(height);
  }

  if (startWeight.equals("")) {
    member.startWeight = member.startWeight;
  }else{
    member.startWeight = Float.parseFloat(startWeight);
  }

  if (gender.equals("")) {
    member.gender = member.gender;
  }else{ member.gender = gender;

  }
  if(member.password.equals(password))
  {member.save();
  }
  Dashboard.index();

}



}