package controllers;
/**
 * Member class that manages
 */

import comparators.SortByDate;
import models.Member;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;
import util.GymUtil;

import java.util.Collections;
//import java.util.Date;
import java.util.List;

public class Dashboard extends Controller
{
  public static void index()
  {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    double bmi = GymUtil.calculateBMI(member,Member.latestAssessment(member));

    String bmiCategory = GymUtil.determineBMICategory(bmi);
    boolean isIdeal= GymUtil.isIdealBodyWeight(member,Member.latestAssessment(member)); //if bodyweight is ideal then isIdeal is true must be 50 (M) or 45 (F)
    List<Assessment> assessments = member.assessments;                                  //isIdeal is true must be 50 (M) or 45 (F)
    Collections.sort(assessments, new SortByDate()); //sorting by assessment dates
    render("dashboard.html", member, assessments, bmi, bmiCategory, isIdeal);
  }

  public static void addAssessment(float weight, float chest, float thigh, float upperArm, float waist, float hip)
  {
    Member member = Accounts.getLoggedInMember();  //checks account for member status
    boolean higher;      //create a boolean condition for progress based on previous assessment and start weight
    if(member.assessments.size() == 0 || member.assessments.size() == 1){
      if(weight > member.startWeight){      //if current weight is greater than startweight
        higher =true;
      }else{
        higher =false;
      }
    }else{                      //if there are no previous assessments and the start weight is less than current weight
      if(member.assessments.get(member.assessments.size()-1).weight < weight){
        higher = true;
      }else{
        higher =false;
      }
    }                                //new assessment with higher field
    Assessment assessment = new Assessment( weight, chest, thigh, upperArm, waist, hip, higher);
    member.assessments.add(assessment);
    member.save();
    Logger.info("Adding Assessment" + assessment);
    redirect("/dashboard");
  }


  /**
   * Method for deleting assessment using id
   * @param id
   * @param assessmentId
   */
  public static void deleteAssessment(Long id, Long assessmentId)
  {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentId);
    member.assessments.remove(assessment);
    assessment.delete();
    member.save();
    Logger.info("Deleting " + assessment);
    redirect("/dashboard");
  }

}
