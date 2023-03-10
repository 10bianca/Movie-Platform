package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;


/**
 * command design pattern
 * Invoker class
 */

public class Undo {

    private ArrayNode output;

    /**
     *
     */
    public Undo() {

    }

    /**
     *
     * @param output
     */
    public Undo(final ArrayNode output) {
        this.output = output;
    }

    /**
     *
     * @param command
     */
    public void edit(final Command command) {
        command.execute(output);
    }

    /**
     * @return
     */
    public int error() {
        return 1;
    }

    /**
     * @param changePage
     */
    public void undo(final ChangePage changePage) {

        changePage.undo(output);
    }


}
