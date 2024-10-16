import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SuppressWarnings("ALL")
public class JavaHub {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final ObjectMapper mapper = new ObjectMapper();

    private static class Response {
        public final boolean error = false;
        public Object data;
    }

    private static class Error {
        public final boolean error = true;
        public String message;
    }

    private String readPipe() throws IOException {
        String line;
        while ((line = reader.readLine()) == null) ;
        return line;
    }

    public <T> T receive(Class<T> cls) throws IOException {
        String rawJSON = readPipe();
        return mapper.readValue(rawJSON, cls);
    }

    public <T> List<T> receiveList(Class<T> cls) throws IOException {
        String rawJSON = readPipe();
        return mapper.readValue(rawJSON, mapper.getTypeFactory().constructCollectionType(List.class, cls));
    }

    public void sendResponse(Object obj) {
        Response response = new Response();
        response.data = obj;
        try {
            String responseJSON = mapper.writeValueAsString(response);
            System.out.println(responseJSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendError(String message) {
        Error error = new Error();
        error.message = message;
        try {
            String errorJSON = mapper.writeValueAsString(error);
            System.out.println(errorJSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}