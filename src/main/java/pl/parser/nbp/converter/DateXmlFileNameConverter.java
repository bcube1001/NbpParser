package pl.parser.nbp.converter;

import pl.parser.nbp.receiver.NbpReceiver;
import pl.parser.nbp.type.TableType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DateXmlFileNameConverter {

    private NbpReceiver nbpReceiver;

    public DateXmlFileNameConverter() {
        nbpReceiver = new NbpReceiver();
    }

    public Set<String> getXmlFileNamesForGivenDateRange(String startDate, String endDate) {
        LocalDate localStartDate = convertToLocalDate(startDate);
        LocalDate localEndDate = convertToLocalDate(endDate);
        int startYear = localStartDate.getYear();
        List<String> listOfXmlFileNames = nbpReceiver.getListOfXmlFileNamesForGivenYear(startYear, TableType.C);
        Set<String> xmlFileNamesForGivenRange = new HashSet<>();
        String xmlFileName;
        do {
            xmlFileName = getXmlFileNameContainingString(listOfXmlFileNames, localStartDate.toString());
            if (startYear != localStartDate.getYear()) {
                startYear = localStartDate.getYear();
                listOfXmlFileNames = nbpReceiver.getListOfXmlFileNamesForGivenYear(startYear, TableType.C);
            }
            if (xmlFileName != null) { // not for every day we have xml file
                xmlFileNamesForGivenRange.add(listOfXmlFileNames.get(listOfXmlFileNames.indexOf(xmlFileName)));
            }
            localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
        } while (!localStartDate.equals(localEndDate.plus(1, ChronoUnit.DAYS)));
        return xmlFileNamesForGivenRange;
    }

    public LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public String getXmlFileNameContainingString(List<String> listOfXmlFiles, String date) {
        List<String> filteredListOfFilenames;
        filteredListOfFilenames = listOfXmlFiles.stream().filter(xmlFileName ->
                xmlFileName.contains(date.replaceAll("-", "").substring(2))).collect(Collectors.toList());
        return filteredListOfFilenames.size() == 0 ? null : filteredListOfFilenames.get(0);
    }
}
