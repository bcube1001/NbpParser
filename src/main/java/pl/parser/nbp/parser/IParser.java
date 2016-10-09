package pl.parser.nbp.parser;

import pl.parser.nbp.type.CourseType;

public interface IParser {
    double getCurrencyRate(String currencyCode, CourseType courseType);
}
