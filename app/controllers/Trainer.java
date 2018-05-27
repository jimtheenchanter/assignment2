package controllers;

import models.Assessment;
import models.Member;
import play.Logger;
import play.mvc.Controller;
import util.GymUtil;
import java.util.List;

public class Trainer extends Controller
{
  public static void index()
  {
    Logger.info("Rendering TrainerDash");
    List<Member> members = Member.findAll();
    render("trainer.html", members);
  }

  public static void showMember(Long memberId){
    Logger.info(String.valueOf(memberId));
    Logger.info("rendering member");
    Member member = Member.findById(memberId);
    double bmi = GymUtil.calculateBMI(member,Member.latestAssessment(member));
    String bmiCategory = GymUtil.determineBMICategory(bmi);
    boolean isIdeal = GymUtil.isIdealBodyWeight(member,Member.latestAssessment(member));
    List<Assessment> assessments = member.assessments;
    boolean isTrainer = true;
    render("dashboard.html", member, assessments, bmi, bmiCategory, isIdeal, isTrainer);
  }


  public static void deleteMember(Long memberId){
    Member member = Member.findById(memberId);
    member.delete();
    redirect("/trainer");
  }

  public static void addComment(Long assessmentId, String comment, long memberId){
    Assessment assessment = Assessment.findById(assessmentId);
    assessment.comment = comment;
    assessment.save();
    showMember(memberId);
  }
}
