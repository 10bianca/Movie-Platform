import input.Input;
import input.Start;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;

public class Main {

    private static int i = 1;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputJson = objectMapper.readValue(new File(args[0]), Input.class);

        ArrayNode outputJson = objectMapper.createArrayNode();

        Start start = new Start(inputJson, outputJson);
        start.startPlatform();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), outputJson);
        i++;
    }
}
