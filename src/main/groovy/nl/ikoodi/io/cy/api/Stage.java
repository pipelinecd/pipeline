package nl.ikoodi.io.cy.api;

public interface Stage {

    String getName();

    String getDescription();

    void setDescription(String description);

    void run(String command) throws Exception;

}
