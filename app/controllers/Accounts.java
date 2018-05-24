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

  public static void logout()
  {
    session.clear();
    redirect ("/");
  }

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


/*  public static void editUser(String firstName, String lastName, String email, String password, String height, String startWeight, String gender)
  {

    Member member = Accounts.getLoggedInMember();

    member.firstName     =(firstName.equals(""))    ? member.firstName   : firstName;
    member.lastName      =(lastName.equals(""))     ? member.lastName    : lastName;
    member.email         =(email.equals(""))        ? member.email       : email;
    member.height        =(height.equals(""))       ? member.height      : Float.parseFloat(height);
    member.startWeight   =(startWeight.equals(""))  ? member.startWeight : Float.parseFloat(startWeight);
    member.gender        =(gender.equals(""))       ? member.gender      : gender;
    if(member.password.equals(password))
    {
      member.save();
    }
    Dashboard.index();

  }*/
public static void editUser(String firstName, String lastName, String email, String password, String height, String startWeight, String gender)
{

  Member member = Accounts.getLoggedInMember();

  if(firstName.equals("")){
    member.firstName = member.firstName;
  }else{
    member.firstName = firstName;
  }
  member.firstName     =(firstName.equals(""))    ? member.firstName   : firstName;
  member.lastName      =(lastName.equals(""))     ? member.lastName    : lastName;
  member.email         =(email.equals(""))        ? member.email       : email;
  member.height        =(height.equals(""))       ? member.height      : Float.parseFloat(height);
  member.startWeight   =(startWeight.equals(""))  ? member.startWeight : Float.parseFloat(startWeight);
  member.gender        =(gender.equals(""))       ? member.gender      : gender;
  if(member.password.equals(password))
  {
    member.save();
  }
  Dashboard.index();

}

}