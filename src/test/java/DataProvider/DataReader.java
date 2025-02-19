package DataProvider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {
    public static List<HashMap<String, String>> readData(String dataFile) throws IOException {
        //Read the LoginData.json to a String
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + dataFile), StandardCharsets.UTF_8);
        //String to HashMap - Using Jakson Databind dependency
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
        return data;
    }
}
