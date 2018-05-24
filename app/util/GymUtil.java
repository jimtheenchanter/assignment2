package util;

import models.Assessment;
import models.Member;

public class GymUtil {

    public static double calculateBMI(Member m, Assessment a){
        double bmiValue = a.weight/(m.getHeight() * m.getHeight());
        return bmiValue;
    }

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
