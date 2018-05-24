package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Assessment extends Model
{
  public Date dateOfAssessment;
  public float weight;
  public float chest;
  public float thigh;
  public float upperArm;
  public float waist;
  public float hip;
  public boolean higher;
  public String comment;


  public Assessment( float weight, float chest, float thigh,float upperArm, float waist, float hip, boolean higher )
  {

    this.dateOfAssessment = new Date();
    this.weight = weight;
    this.chest = chest;
    this.thigh = thigh;
    this.upperArm = upperArm;
    this.waist = waist;
    this.hip = hip;
    this.comment ="";
    this.higher = higher;
  }


  public Date getDateOfAssessment(){
      return dateOfAssessment;
  }

}
