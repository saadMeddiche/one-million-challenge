package org.saadMeddiche.utils;

import java.nio.ByteBuffer;

/*
* I didn't really test if the bytes generated are really respecting the RFC specification.
*  This project is not about UUID, so I don't care lol.
* */
public class UUIDTool {

    private final static byte[] HEX_ARRAY = "0123456789abcdef".getBytes();

    private final static byte MINUS = '-';

    public static void writeUUID(ByteBuffer buffer) {

        long msb = FasterRandom.numberAsLong();
        long lsb = FasterRandom.numberAsLong();

        writeUUID(msb, lsb, buffer);

    }

    private static void writeUUID(long msb, long lsb, ByteBuffer buffer) {

        // 8-4-4-4-12

        // 8
        hexPopulate(msb >>> 32, 8, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(msb >>> 16, 4, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(msb, 4, buffer);
        buffer.put(MINUS);

        // 4
        hexPopulate(lsb >>> 48, 4, buffer);
        buffer.put(MINUS);

        // 12
        hexPopulate(lsb, 12, buffer);

    }

    private static void hexPopulate(long seed, int numbers, ByteBuffer buffer) {

        for (int i = numbers - 1; i >= 0; i--) {
            buffer.put(HEX_ARRAY[(int) ((seed >>> (i * 4)) & 0xF)]);
        }

    }

    // create a byte[36] that represents a string in UTF-8
    public static byte[] uuidAsBytes() {

        byte[] arr = new byte[36];

        long msb = FasterRandom.numberAsLong();
        long lsb = FasterRandom.numberAsLong();

        writeUUID(msb, lsb, arr);

        return arr;

    }

    private static void writeUUID(long msb, long lsb, byte[] arr) {

        int pos = 0;

        pos = hexPopulate(msb >>> 32, 8, arr, pos);
        arr[pos++] = MINUS;

        pos = hexPopulate(msb >>> 16, 4, arr, pos);
        arr[pos++] = MINUS;

        pos = hexPopulate(msb, 4, arr, pos);
        arr[pos++] = MINUS;

        pos = hexPopulate(lsb >>> 48, 4, arr, pos);
        arr[pos++] = MINUS;

        hexPopulate(lsb, 12, arr, pos);

    }

    private static int hexPopulate(long seed, int numbers, byte[] arr, int pos) {

        for (int i = numbers - 1; i >= 0; i--) {
            arr[pos++] = HEX_ARRAY[(int) ((seed >>> (i * 4)) & 0xF)];
        }

        return pos;

    }

}