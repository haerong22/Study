package org.example.trxbatch.util;

import org.example.trxbatch.exception.TrxBatchInvalidArgumentException;

public class MaskUtil {

    private static final String ASTERISK = "*";

    public static String maskTailWithAsterisk(String inputString, int maskLength) {
        if (maskLength < 0) {
            throw new TrxBatchInvalidArgumentException("maskLength must be non-negative");
        }

        if (inputString == null) {
            return null;
        }

        if (inputString.length() < maskLength) {
            return ASTERISK.repeat(maskLength);
        } else {
            return inputString.substring(0, inputString.length() - maskLength) + ASTERISK.repeat(maskLength);
        }
    }

}