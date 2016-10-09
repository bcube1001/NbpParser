package pl.parser.nbp;

import pl.parser.nbp.currencyutils.CurrencyUtils;

import java.text.DecimalFormat;

public class MainClass {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.print("Invalid arguments, example usage: " +
                    "java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31");
        }
        CurrencyUtils currencyUtils = new CurrencyUtils(args[0], args[1], args[2]);
        System.out.println(new DecimalFormat("0.0000").format(currencyUtils.evaluateAverageBuyingCourse()));
        System.out.println(new DecimalFormat("0.0000").format(currencyUtils.evaluateStandardDeviation()));
    }
}