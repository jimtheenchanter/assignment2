package comparators;

import models.Assessment;

import java.util.Comparator;

public class SortByDate implements Comparator<Assessment> {
    @Override
    public int compare(Assessment o1, Assessment o2)
    {
        return o2.dateOfAssessment.compareTo(o1.dateOfAssessment);
    }


}
