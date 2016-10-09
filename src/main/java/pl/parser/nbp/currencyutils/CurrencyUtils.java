package pl.parser.nbp.currencyutils;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import pl.parser.nbp.converter.DateXmlFileNameConverter;
import pl.parser.nbp.parser.CoursesTableXmlParser;
import pl.parser.nbp.receiver.NbpReceiver;
import pl.parser.nbp.type.CourseType;

import java.util.ArrayList;
import java.util.List;

public class CurrencyUtils {
    private List<Double> buyingCourseList = new ArrayList<>();
    private List<Double> sellingCourseList = new ArrayList<>();


    public CurrencyUtils(String startDate, String endDate, String currencyCode) {
        getCourseListInDateRange(startDate, endDate, currencyCode);
    }

    public void getCourseListInDateRange(String startDate, String endDate,
                                         String currencyCode) {
        final CoursesTableXmlParser[] coursesTableXmlParser = {null};
        new DateXmlFileNameConverter().getXmlFileNamesForGivenDateRange(startDate, endDate).forEach(xmlFileName -> {
            coursesTableXmlParser[0] = new CoursesTableXmlParser(new NbpReceiver().getCoursesTableAsXml(xmlFileName));
            buyingCourseList.add(coursesTableXmlParser[0].getCurrencyRate(currencyCode, CourseType.BUYING));
            sellingCourseList.add(coursesTableXmlParser[0].getCurrencyRate(currencyCode, CourseType.SELLING));
        });
    }

    public double evaluateAverageBuyingCourse() {
        return buyingCourseList.stream()
                .mapToDouble(a -> a)
                .average().getAsDouble();
    }

    public double evaluateStandardDeviation() {
        double[] sellingCourseArray = sellingCourseList.stream().mapToDouble(Double::doubleValue).toArray(); //via method reference
        return new StandardDeviation(false).evaluate(sellingCourseArray);
    }
}