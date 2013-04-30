package nl.ikoodi.io.cy.model;

import nl.ikoodi.io.cy.api.Stage;

public class DefaultStage implements Stage {
    private final String name;
    private String description;

    public DefaultStage(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    @Deprecated
    public void echo(final String format, final Object... values) {
        System.out.printf(format, values);
    }

}
