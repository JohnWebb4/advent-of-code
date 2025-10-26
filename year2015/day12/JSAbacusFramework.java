/* Licensed under Apache-2.0 */
package advent.year2015.day12;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSAbacusFramework {
    public static float sumJSONNumbers(String input) {
        float sum = 0;
        ObjectMapper mapper = new ObjectMapper();

        for (String line : input.split("\n")) {
            try {
                JsonNode rootNode = mapper.readTree(line);
                Stack<JsonNode> nodesToCheck = new Stack<>();
                nodesToCheck.add(rootNode);

                while (nodesToCheck.size() > 0) {
                    JsonNode node = nodesToCheck.pop();

                    sum += node.floatValue();

                    for (JsonNode childNode : node) {
                        nodesToCheck.add(childNode);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return sum;
    }

    public static float sumJSONNumbersIgnoreRed(String input) {
        float sum = 0;
        ObjectMapper mapper = new ObjectMapper();

        for (String line : input.split("\n")) {
            try {
                JsonNode rootNode = mapper.readTree(line);
                Stack<JsonNode> nodesToCheck = new Stack<>();
                nodesToCheck.add(rootNode);

                while (nodesToCheck.size() > 0) {
                    JsonNode node = nodesToCheck.pop();

                    if (!node.isArray() && node.has("red")) {
                        break;
                    }

                    sum += node.floatValue();

                    List<JsonNode> childNodes = new LinkedList<>();
                    boolean doesNotHaveRed = true;
                    for (JsonNode childNode : node) {
                        if (!node.isArray() && childNode.textValue() != null && childNode.textValue().equals("red")) {
                            doesNotHaveRed = false;
                            break;
                        }

                        childNodes.add(childNode);
                    }

                    if (doesNotHaveRed) {
                        nodesToCheck.addAll(childNodes);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return sum;
    }
}
