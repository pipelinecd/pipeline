package nl.ikoodi.io.cy.cli.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Parameters(
        commandNames = {RunCommand.CMD_NAME}
        , commandDescription = "Run, baby run!"
)
public class RunCommand {
    public static final String CMD_NAME = "run";

    @Parameter(
            description = ""
            , listConverter = FileConverter.class
            , required = true
            , arity = 2
    )
    private List<File> files = new ArrayList<File>(2);

    public List<File> getFiles() {
        return files;
    }
}
