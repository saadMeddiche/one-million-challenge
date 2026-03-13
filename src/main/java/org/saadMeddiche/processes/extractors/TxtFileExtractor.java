package org.saadMeddiche.processes.extractors;

import org.saadMeddiche.models.TxtFileExtractorResult;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Extract numbers from .txt file, summing them, then return the sum.
 * */
public abstract class TxtFileExtractor {

    private final static Logger logger = Logger.getLogger(TxtFileExtractor.class.getName());

    protected final String COLUMN_SEPARATOR = ";";
    protected final String TXT_EXTENSION = ".txt";

    /**
     * This method contains the main logic of {@link TxtFileExtractor}.
     * The concrete classes should extract numbers from a file and return the sum of them.
     * */
    protected abstract TxtFileExtractorResult mainProcess(File file, String columnSeparator);

    /**
     * This method is an overload of {@link #extract(String, String)}, in case the column separator is not provided
     * */
    public final TxtFileExtractorResult extract(String filename) {
        return extract(filename, COLUMN_SEPARATOR);
    }

    /**
     * This method does the necessary validations before it delegate the extraction work to {@link #mainProcess(File, String)}
     * */
    public final TxtFileExtractorResult extract(String filename, String columnSeparator) {

        String treatedFilename = this.treatFilename(filename);

        Optional<File> optionalFile = this.getFile(treatedFilename);

        if(optionalFile.isEmpty()) {
            return new TxtFileExtractorResult(false, 0, Optional.of("file not found"));
        }

        File file = optionalFile.get();

        return this.mainProcess(file, columnSeparator);
    }


    // ============================ HELPERS ============================

    private Optional<File> getFile(String filename) {

        File file = new File(filename);

        if(file.exists()) {
            return Optional.of(file);
        }

        return Optional.empty();

    }

    private String treatFilename(String filename) {

        if(filename == null || filename.isEmpty()) {
            return UUID.randomUUID() + TXT_EXTENSION;
        }

        if(filename.endsWith(TXT_EXTENSION)) {
            return filename;
        }

        return filename + TXT_EXTENSION;

    }

}
