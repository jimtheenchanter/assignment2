package util;


/**
 * This Utility class generates analytics on the status of the member based on the information taken in
 * through the assessment and sign up forms.
 */

import models.Assessment;
import models.Member;

public class GymUtil {

    /**
     * Calulates members BMI based on assessment
     * @param m member class
     * @param a assessment
     * @return BMI value as a double
     */
    public static double calculateBMI(Member m, Assessment a){
        double bmiValue = (double) a.weight/(m.getHeight() * m.getHeight());
        return bmiValue;
    }

    /**
     * Uses calculated BMI value to categorise member's body type
     * @param bmiValue
     * @return String declaration
     */

    public static String determineBMICategory(double bmiValue){
        String result = "";
        if(bmiValue < 16){
            result = "SEVERELY UNDERWEIGHT";
        }else if(bmiValue >= 16 && bmiValue < 18.5){
            result = "UNDERWEIGHT";
        }else if(bmiValue >= 18.5 && bmiValue < 25){
            result = "NORMAL";
        }else if(bmiValue >= 25 && bmiValue < 30){
            result = "OVERWEIGHT";
        }else if(bmiValue >= 30 && bmiValue < 35){
            result = "MODERATELY OBESE";
        }else if(bmiValue >= 35){
            result = "SEVERELY OBESE";
        }
        return result;
    }

    /**
     * Uses parameters to calculate if member is ideal body weight 50 (m) or 45 (f) (+- 0.2)
     * @param member
     * @param assessment
     * @return
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment){
        float idealWeight;
        if(member.getGender().equalsIgnoreCase("M")){
            if(member.getHeight() > 1.524 ){   //if member is greater than 5ft carry out the following computation
                idealWeight = (float) (50 + (((member.getHeight()-1.524)/0.0254) * 2.3));  // 2.3 kg for every inch over 5ft
            }else{
                idealWeight = 50;
            }
            if(idealWeight > assessment.weight - 0.2f && idealWeight < assessment.weight + 0.2f){
                return true;
            }
        }else if(member.getGender().equalsIgnoreCase("F") || member.getGender().equals("Unspecified")){
            if(member.getHeight() > 1.524 ){
                idealWeight = (float) (45.5 + (((member.getHeight()-1.524)/0.0254) * 2.3));
            }
            else{
                idealWeight = (float) 45.5;
            }
            if(idealWeight > assessment.weight - 0.2f && idealWeight < assessment.weight + 0.2f)
                return true;
        }
        return false;
    }





}
