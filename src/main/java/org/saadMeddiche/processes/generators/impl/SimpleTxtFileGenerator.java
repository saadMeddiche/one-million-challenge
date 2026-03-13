package org.saadMeddiche.processes.generators.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.generators.TxtFileGenerator;
import org.saadMeddiche.utils.FasterRandom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge. It uses strait forward generation.
 * <p>
 * Loop to generate content is a single string — <b>using List to store lines</b>, then writes the content into the file.
 * This means the higher lines to generate. The higher memory/time it takes.
 * */
public class SimpleTxtFileGenerator extends TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(SimpleTxtFileGenerator.class.getName());

    protected TxtFileGeneratorResult mainProcess(File file, long linesNumber) {

        logger.info("Generating content for file: " + file.getAbsolutePath());
        List<String> lines = this.buildLines(linesNumber);

        logger.info("Writing content to file: " + file.getAbsolutePath());
        boolean isContentWritten = this.writeToFile(file, lines);

        if(isContentWritten) {
            return new TxtFileGeneratorResult(true, Optional.empty());
        } else  {
            return new TxtFileGeneratorResult(false, Optional.of("failed to write content into txt file"));
        }

    }

    private boolean writeToFile(File file, List<String> lines) {

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            for(String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.write(System.lineSeparator());
            }

            return true;

        }
        catch (IOException ex) {
            logger.severe(String.format("failed to write to file %s due to reason %s", file.getAbsolutePath(), ex.getMessage()));
            return false;
        }

    }

    private List<String> buildLines(long lines) {

        List<String> newLines = new ArrayList<>();

        for(long i = 0 ; i < lines; i++) {
            newLines.add(buildLine(i));
        }

        return newLines;

    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + FasterRandom.uuid() + COLUMN_SEPARATOR + FasterRandom.number();
    }

}