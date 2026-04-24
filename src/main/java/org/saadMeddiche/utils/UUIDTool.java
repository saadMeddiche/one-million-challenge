package org.saadMeddiche.utils;

import java.nio.ByteBuffer;

public class UUIDTool {

    private final static byte[] HEX_ARRAY = "0123456789abcdef".getBytes();

    private final static byte MINUS = '-';

    public static void writeUUID(ByteBuffer buffer) {

        long seed = FasterRandom.numberAsLong();

        // 8-4-4-4-12

        // 8
        hexPopulate(seed >> 32 ,8, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(seed >> 16 ,4, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(seed >> 12 ,4, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(seed >> 8,4, buffer);
        buffer.put(MINUS);

        // 12
        hexPopulate(seed, 12, buffer);

    }

    private static void hexPopulate(long seed, int numbers, ByteBuffer buffer) {

        for(int i = 0; i < numbers; i++) {
            buffer.put(HEX_ARRAY[(int)(seed & 0xF)]);
            seed >>= 4;
        }

    }

    // create a byte[36] that represents a string in UTF-8
    public static byte[] uuidAsBytes() {

        long seed = FasterRandom.numberAsLong();

        byte[] arr = new byte[36];
        // 8-4-4-4-12

        // 8
        hexPopulate(seed >> 32 ,8, arr, 0);
        arr[8]  = '-';

        // 4
        hexPopulate(seed >> 16 ,4, arr, 9);
        arr[13] = '-';

        // 4
        hexPopulate(seed >> 12 ,4, arr, 14);
        arr[18] = '-';

        // 4
        hexPopulate(seed >> 8,4, arr, 19);
        arr[23] = '-';

        // 12
        hexPopulate(seed, 12, arr, 24);

        return arr;

    }

    private static void hexPopulate(long seed, int numbers, byte[] arr, int pos) {

        for(int i = 0; i < numbers; i++) {
            arr[i + pos] = HEX_ARRAY[(int)(seed & 0xF)];
            seed >>= 4;
        }

    }

}
