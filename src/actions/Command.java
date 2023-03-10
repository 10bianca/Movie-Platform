package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface Command {
    /**
     * @param output
     */
    void execute(ArrayNode output);

    /**
     * @param output
     */
    void undo(ArrayNode output);
}
