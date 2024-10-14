import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JavaHub {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    ObjectMapper mapper = new ObjectMapper();

    public <T> T receive(Class<T> cls) throws IOException {
        String rawJSON = reader.readLine();
        return mapper.readValue(rawJSON, cls);
    }

    public <T> List<T> receiveList(Class<T> cls) throws IOException {
        String rawJSON = reader.readLine();
        return mapper.readValue(rawJSON, mapper.getTypeFactory().constructCollectionType(List.class, cls));
    }

    public void send(Object obj) throws IOException {
        String rawJSON = mapper.writeValueAsString(obj);
        System.out.println(rawJSON);
    }
}