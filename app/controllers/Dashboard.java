package controllers;

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
    boolean isIdeal= GymUtil.isIdealBodyWeight(member,Member.latestAssessment(member));; //if BMI category is normal then isIdeal is true
//    if (bmiCategory.equalsIgnoreCase( "NORMAL")) isIdeal = true;
//    else isIdeal = false;
//    String gender = member.getGender();
    List<Assessment> assessments = member.assessments;
    Collections.sort(assessments, new SortByDate()); //sorting by assessment dates
    render("dashboard.html", member, assessments, bmi, bmiCategory, isIdeal);
  }

  public static void addAssessment(float weight, float chest, float thigh, float upperArm, float waist, float hip)
  {
    Member member = Accounts.getLoggedInMember();
    boolean higher;
    if(member.assessments.size() == 0 || member.assessments.size() == 1){
      if(weight > member.startWeight){
        higher =true;
      }else{
        higher =false;
      }
    }else{
      if(member.assessments.get(member.assessments.size()-1).weight < weight){
        higher = true;
      }else{
        higher =false;
      }
    }
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
    /*member.save();*/
    assessment.delete();
    member.save();
    Logger.info("Deleting " + assessment);
    redirect("/dashboard");
  }

}
