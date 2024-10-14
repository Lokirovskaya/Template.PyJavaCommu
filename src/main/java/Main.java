import java.io.IOException;
import java.util.List;

public class Main {

    private static class DataClass {
        public String data;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        JavaHub hub = new JavaHub();

        while (true) {
            Thread.sleep(1000);
            List<Integer> xs = hub.receiveList(Integer.class);

            int sum = 0;
            for (int x : xs) {
                sum += x;
            }

            var reply = new DataClass();
            reply.data = sum + "";
            hub.send(reply);
        }
    }
}