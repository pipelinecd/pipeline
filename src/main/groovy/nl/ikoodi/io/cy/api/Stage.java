package nl.ikoodi.io.cy.api;

public interface Stage {

    String getName();

    String getDescription();

    void setDescription(String description);

    /**
     * Example of a self defined echo.
     *
     * @param format
     * @param values
     * @deprecated just for trying out?
     */
    @Deprecated
    void echo(String format, Object... values);

    void run(String command) throws Exception;

}
