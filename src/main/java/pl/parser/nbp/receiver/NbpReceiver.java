package pl.parser.nbp.receiver;

import org.apache.commons.io.IOUtils;
import pl.parser.nbp.type.TableType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NbpReceiver {
    private final String xmlApiBaseUrl = "http://www.nbp.pl/kursy/xml/";
    private URL url = null;
    private HttpURLConnection connection = null;

    public String getCoursesTableAsXml(String xmlFileName) {
        String xml = null;
        try {
            url = new URL(xmlApiBaseUrl + xmlFileName + ".xml");
            connection = (HttpURLConnection) url.openConnection();
            xml = IOUtils.toString(connection.getInputStream(), UTF_8.name());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return xml;
    }

    public List<String> getListOfXmlFileNamesForGivenYear(int year, final TableType tableType) {
        List<String> listOfXmlFileNames = new ArrayList<>();
        try {
            url = new URL(xmlApiBaseUrl + "dir" + (year == LocalDateTime.now().getYear() ? "" : year) + ".txt");
            connection = (HttpURLConnection) url.openConnection();
            listOfXmlFileNames = IOUtils.readLines(connection.getInputStream(), UTF_8.name());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return listOfXmlFileNames.stream().filter(line ->
                line.startsWith(tableType.toString().toLowerCase()))
                .collect(Collectors.toList());
    }
}
