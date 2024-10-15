import com.fasterxml.jackson.core.JsonProcessingException;
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

    private static class Response {
        public final boolean error = false;
        public Object data;
    }

    private static class Error {
        public final boolean error = true;
        public String message;
    }

    public void sendResponse(Object obj) throws IOException {
        Response response = new Response();
        response.data = obj;
        String responseJSON = mapper.writeValueAsString(response);
        System.out.println(responseJSON);
    }

    public void sendError(String message)  {
        Error error = new Error();
        error.message = message;
        String errorJSON;
        try {
            errorJSON = mapper.writeValueAsString(error);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(errorJSON);
    }
}