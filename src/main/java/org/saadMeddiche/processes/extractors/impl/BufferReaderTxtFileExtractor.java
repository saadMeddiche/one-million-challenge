package org.saadMeddiche.processes.extractors.impl;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

public class BufferReaderTxtFileExtractor extends TxtFileExtractor {

    private final static int ALLOCATION_SIZE = 5_000_000;

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {

        try (BufferedReader br = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {

            CharBuffer cf = CharBuffer.allocate(ALLOCATION_SIZE);

            long numbersSum = 0;

            int lineCount = 0;

            int numberOfDigits = 1;

            int numberThreshHold = 10;

            while(br.read(cf) > 0) {

                cf.flip();

                int last_index = cf.limit() - 1;

                char lastChar = cf.get(last_index);

                int completedLinesPosition = cf.limit();

                if(lastChar != '\n') {

                    int unCompleteLineLength = 0;

                    for(int i = last_index ; i >= 0 ; i--) {

                        char c = cf.get(i);

                        if(c =='\n') break;

                        unCompleteLineLength++;

                    }

                    completedLinesPosition = cf.limit() - unCompleteLineLength;

                }

                while(cf.position() < completedLinesPosition) {

                    if(lineCount == numberThreshHold) {
                        numberOfDigits++;
                        numberThreshHold *= 10;
                    }

                    int thirdColumnPosition = cf.position() + numberOfDigits + 36 + 2;

                    byte numberNature = numberNature(cf.get(thirdColumnPosition));

                    cf.position(thirdColumnPosition + (numberNature & 0b0000_0001) );

                    char c = cf.get();

                    int number = 0;

                    do {
                        number = number * 10 + (c - '0');
                        c = cf.get();
                    }
                    while (c != '\r' && c != '\n');

                    if(c != '\n') cf.position(cf.position() + 1);

                    number *= (numberNature & 0b0000_0010) == 0b0000_0010 ? 1 : -1;

                    numbersSum += number;

                    lineCount++;

                }

                cf.position(completedLinesPosition);
                cf.compact();


            }

            return new TxtFileExtractorResult(true , numbersSum,  Optional.empty());

        }
        catch (IOException e) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

    protected byte numberNature(char c) {

        if(c == '-') return 0b0000_0001;

        if(c == '+') return 0b0000_0011;

        return 0b0000_0010;

    }

}
