package org.saadMeddiche.processes.extractors.impl;

import org.saadMeddiche.models.TxtFileExtractorResult;
import org.saadMeddiche.processes.extractors.TxtFileExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class ByteChannelTxtFileExtractor extends TxtFileExtractor {

    //private final static int ALLOCATION_SIZE     = 500;
    private final static int ALLOCATION_SIZE     = 100_000;
    private final static int LAST_INDEX          = ALLOCATION_SIZE - 1;

    protected TxtFileExtractorResult mainProcess(File file, String columnSeparator) {


        try (SeekableByteChannel ch = Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {

            ByteBuffer bf = ByteBuffer.allocate(ALLOCATION_SIZE);

            int numbersSum = 0;

            while(ch.read(bf) > 0) {

                bf.flip();

                int last_index = bf.limit() - 1;

                byte lastByte = bf.get(last_index);

                if(lastByte != '\r' && lastByte != '\n') {

                    int unCompleteLineLength = 0;

                    for(int i = last_index ; i >= 0 ; i--) {

                        byte b = bf.get(i);

                        if(b == '\r' || b =='\n') break;

                        unCompleteLineLength++;

                    }

                    bf.position(bf.limit() - unCompleteLineLength);
                    bf.compact();

                }


            }

            return new TxtFileExtractorResult(true, numbersSum, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }


    protected TxtFileExtractorResult mainProcesss(File file, String columnSeparator) {

        byte columnSeparatorByte = columnSeparator.getBytes()[0];

        try (SeekableByteChannel ch = Files.newByteChannel(file.toPath(), StandardOpenOption.READ)) {

            ByteBuffer bf = ByteBuffer.allocate(ALLOCATION_SIZE);

            int numbersSum = 0;

            int lineCount = 0;

            while(ch.read(bf) > 0) {
                bf.flip();

                int number = 0;

                int numberOfDigits = lineCount == 0 ? 1 : (int) (Math.log10(lineCount) + 1);

                boolean isNegative = bf.get(numberOfDigits + 38) == '-';

                bf.position(numberOfDigits + 38 + (isNegative ? 1 : 0));

                while(bf.hasRemaining()) {

                    byte b = bf.get();

                    if(b == '\r') {

                        lineCount++;

                        numberOfDigits = lineCount == 0 ? 1 : (int) (Math.log10(lineCount) + 1);

                        isNegative = bf.get(numberOfDigits + 38) == '-';

                        bf.position(bf.position() + numberOfDigits + 38 + (isNegative ? 1 : 0) + 1);

                        if(isNegative) {
                            number *= -1;
                        }

                        numbersSum += number;
                        number = 0;

                        continue;
                    }

                    number = number * 10 + (b - '0');

                }

                byte lastByte = bf.get(LAST_INDEX);

                if(lastByte != '\r' && lastByte != '\n') {

                    int unCompleteLineLength = 0;

                    for(int i = LAST_INDEX ; i >= 0 ; i--) {

                        if(bf.get(i) == '\n') break;

                        unCompleteLineLength++;

                    }

                    bf.position(ALLOCATION_SIZE - unCompleteLineLength);
                    bf.compact();

                }

                bf.clear();
            }


            return new TxtFileExtractorResult(true, numbersSum, Optional.empty());

        }
        catch ( IOException e ) {
            return new TxtFileExtractorResult(false , 0,  Optional.of(e.getMessage()));
        }

    }

}