package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * JsonReader — reads JSON test-data files and exposes nodes by key.
 * Test data is parametrized from JSON as required by the project spec.
 */
public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonReader() {}

    /**
     * Returns a specific node from a JSON file.
     * e.g. JsonReader.get("src/test/resources/testdata/users.json", "validUser")
     */
    public static JsonNode get(String filePath, String nodeName) {
        try {
            JsonNode root = mapper.readTree(new File(filePath));
            JsonNode node = root.get(nodeName);
            if (node == null) {
                throw new RuntimeException("Node '" + nodeName + "' not found in: " + filePath);
            }
            return node;
        } catch (IOException e) {
            throw new RuntimeException("Could not read JSON from: " + filePath, e);
        }
    }

    /** Convenience: reads users.json */
    public static JsonNode getUser(String nodeName) {
        return get(ConfigReader.get("testdata.users"), nodeName);
    }

    /** Convenience: reads products.json */
    public static JsonNode getProduct(String nodeName) {
        return get(ConfigReader.get("testdata.products"), nodeName);
    }

    /** Convenience: reads payment.json */
    public static JsonNode getPayment(String nodeName) {
        return get(ConfigReader.get("testdata.payment"), nodeName);
    }

    /** Convenience: reads contact.json */
    public static JsonNode getContact(String nodeName) {
        return get(ConfigReader.get("testdata.contact"), nodeName);
    }
}
