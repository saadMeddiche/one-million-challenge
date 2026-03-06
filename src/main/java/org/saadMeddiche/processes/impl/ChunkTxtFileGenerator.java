package org.saadMeddiche.processes.impl;

import org.saadMeddiche.models.TxtFileGeneratorResult;
import org.saadMeddiche.processes.TxtFileGenerator;
import org.saadMeddiche.utils.FasterRandom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generate the .txt file that will be used for the challenge. It uses strait forward generation.
 * <p>
 * It generates content and writes into file in chunks. It takes less/controlled memory at the cost of extra time.
 * */
public class ChunkTxtFileGenerator extends TxtFileGenerator {

    private final static Logger logger = Logger.getLogger(ChunkTxtFileGenerator.class.getName());

    private final static int CHUNK_SIZE = 1_000_000;

    protected TxtFileGeneratorResult mainProcess(File file, long lines) {

        List<String> chunk = new ArrayList<>(CHUNK_SIZE);

        for (int i = 0; i < lines; i++) {

            chunk.add(buildLine(i));

            if( chunk.size() == CHUNK_SIZE ) {
                boolean isContentWritten = writeIntoFile(file, chunk);
                if( !isContentWritten ) {
                    logger.log(Level.WARNING, "Could not write chunk at line " + i + ".");
                } else {
                    logger.log(Level.INFO, "Successfully generated chunk at line " + i + ".");
                }
                chunk.clear();
            }

        }

        if( !chunk.isEmpty() ) {
            writeIntoFile(file,chunk);
            chunk.clear();
        }

        return new TxtFileGeneratorResult(true, Optional.empty());
    }

    private String buildLine(long id) {
        return id + COLUMN_SEPARATOR + FasterRandom.uuid() + COLUMN_SEPARATOR + FasterRandom.number();
    }

    private boolean writeIntoFile(File file, List<String> chunk) {

        try(FileWriter fileWriter = new FileWriter(file, true)) {

            String content = buildContent(chunk);

            fileWriter.write(content);

            return true;

        }
        catch (IOException e) {
            logger.warning("Failed to write to file due tue " + e.getMessage());
            return false;
        }

    }

    private String buildContent(List<String> chunk) {
        return String.join(System.lineSeparator(), chunk);
    }

}