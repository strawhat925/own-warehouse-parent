// copy right
package code.warehouse.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 14:05
 **/
public class Base64 {
    private static final int BASELENGTH = 255;
    private static final int LOOKUPLENGTH = 64;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final int EIGHTBIT = 8;
    private static final int SIXTEENBIT = 16;
    private static final int FOURBYTE = 4;
    private static final int SIGN = -128;
    private static final byte PAD = 61;
    private static final byte[] BASE64_ALPHABET = new byte[255];
    private static final byte[] LOOKUP_BASE64_ALPHABET = new byte[64];
    private static final byte[] encodingTable = new byte[]{ 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

    static {
        int i;
        for (i = 0; i < 255; ++i) {
            BASE64_ALPHABET[i] = -1;
        }

        for (i = 90; i >= 65; --i) {
            BASE64_ALPHABET[i] = (byte) (i - 65);
        }

        for (i = 122; i >= 97; --i) {
            BASE64_ALPHABET[i] = (byte) (i - 97 + 26);
        }

        for (i = 57; i >= 48; --i) {
            BASE64_ALPHABET[i] = (byte) (i - 48 + 52);
        }

        BASE64_ALPHABET[43] = 62;
        BASE64_ALPHABET[47] = 63;

        for (i = 0; i <= 25; ++i) {
            LOOKUP_BASE64_ALPHABET[i] = (byte) (65 + i);
        }

        i = 26;

        int j;
        for (j = 0; i <= 51; ++j) {
            LOOKUP_BASE64_ALPHABET[i] = (byte) (97 + j);
            ++i;
        }

        i = 52;

        for (j = 0; i <= 61; ++j) {
            LOOKUP_BASE64_ALPHABET[i] = (byte) (48 + j);
            ++i;
        }

        LOOKUP_BASE64_ALPHABET[62] = 43;
        LOOKUP_BASE64_ALPHABET[63] = 47;
    }

    private Base64() {
    }

    public static String encodeToString(byte[] data) {
        return data == null ? null : new String(encodeInner(data));
    }

    public static String encodeToString(String data) {
        return data == null ? null : encodeToString(data.getBytes());
    }

    private static byte[] encodeInner(byte[] data) {
        int modulus = data.length % 3;
        byte[] bytes;
        if (modulus == 0) {
            bytes = new byte[4 * data.length / 3];
        } else {
            bytes = new byte[4 * (data.length / 3 + 1)];
        }

        int dataLength = data.length - modulus;
        int b1 = 0;

        int b2;
        for (b2 = 0; b1 < dataLength; b2 += 4) {
            int a1 = data[b1] & 255;
            int a2 = data[b1 + 1] & 255;
            int a3 = data[b1 + 2] & 255;
            bytes[b2] = encodingTable[a1 >>> 2 & 63];
            bytes[b2 + 1] = encodingTable[(a1 << 4 | a2 >>> 4) & 63];
            bytes[b2 + 2] = encodingTable[(a2 << 2 | a3 >>> 6) & 63];
            bytes[b2 + 3] = encodingTable[a3 & 63];
            b1 += 3;
        }

        int d1;
        switch (modulus) {
            case 0:
            default:
                break;
            case 1:
                d1 = data[data.length - 1] & 255;
                b1 = d1 >>> 2 & 63;
                b2 = d1 << 4 & 63;
                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = 61;
                bytes[bytes.length - 1] = 61;
                break;
            case 2:
                d1 = data[data.length - 2] & 255;
                int d2 = data[data.length - 1] & 255;
                b1 = d1 >>> 2 & 63;
                b2 = (d1 << 4 | d2 >>> 4) & 63;
                int b3 = d2 << 2 & 63;
                bytes[bytes.length - 4] = encodingTable[b1];
                bytes[bytes.length - 3] = encodingTable[b2];
                bytes[bytes.length - 2] = encodingTable[b3];
                bytes[bytes.length - 1] = 61;
        }

        return bytes;
    }

    public static boolean isBase64(String isValidString) {
        return isArrayByteBase64(getAsciiBytes(isValidString));
    }

    public static byte[] getAsciiBytes(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        } else {
            try {
                return data.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException("HttpClient requires ASCII support");
            }
        }
    }

    static boolean isBase64(byte octect) {
        return octect == 61 || BASE64_ALPHABET[octect] != -1;
    }

    public static boolean isArrayByteBase64(byte[] arrayOctect) {
        int length = arrayOctect.length;
        if (length == 0) {
            return true;
        } else {
            for (int i = 0; i < length; ++i) {
                if (!isBase64(arrayOctect[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static byte[] encode(byte[] binaryData) {
        int lengthDataBits = binaryData.length * 8;
        int fewerThan24bits = lengthDataBits % 24;
        int numberTriplets = lengthDataBits / 24;
        Object encodedData = null;
        byte[] var16;
        if (fewerThan24bits != 0) {
            var16 = new byte[(numberTriplets + 1) * 4];
        } else {
            var16 = new byte[numberTriplets * 4];
        }

        boolean k = false;
        boolean l = false;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean encodedIndex = false;
        boolean dataIndex = false;
        boolean i = false;

        byte val1;
        byte val2;
        byte var17;
        byte var18;
        byte var19;
        byte var20;
        int var22;
        int var23;
        int var24;
        for (var24 = 0; var24 < numberTriplets; ++var24) {
            var23 = var24 * 3;
            var19 = binaryData[var23];
            var20 = binaryData[var23 + 1];
            byte var21 = binaryData[var23 + 2];
            var18 = (byte) (var20 & 15);
            var17 = (byte) (var19 & 3);
            var22 = var24 * 4;
            val1 = (var19 & -128) == 0 ? (byte) (var19 >> 2) : (byte) (var19 >> 2 ^ 192);
            val2 = (var20 & -128) == 0 ? (byte) (var20 >> 4) : (byte) (var20 >> 4 ^ 240);
            byte val3 = (var21 & -128) == 0 ? (byte) (var21 >> 6) : (byte) (var21 >> 6 ^ 252);
            var16[var22] = LOOKUP_BASE64_ALPHABET[val1];
            var16[var22 + 1] = LOOKUP_BASE64_ALPHABET[val2 | var17 << 4];
            var16[var22 + 2] = LOOKUP_BASE64_ALPHABET[var18 << 2 | val3];
            var16[var22 + 3] = LOOKUP_BASE64_ALPHABET[var21 & 63];
        }

        var23 = var24 * 3;
        var22 = var24 * 4;
        if (fewerThan24bits == 8) {
            var19 = binaryData[var23];
            var17 = (byte) (var19 & 3);
            val1 = (var19 & -128) == 0 ? (byte) (var19 >> 2) : (byte) (var19 >> 2 ^ 192);
            var16[var22] = LOOKUP_BASE64_ALPHABET[val1];
            var16[var22 + 1] = LOOKUP_BASE64_ALPHABET[var17 << 4];
            var16[var22 + 2] = 61;
            var16[var22 + 3] = 61;
        } else if (fewerThan24bits == 16) {
            var19 = binaryData[var23];
            var20 = binaryData[var23 + 1];
            var18 = (byte) (var20 & 15);
            var17 = (byte) (var19 & 3);
            val1 = (var19 & -128) == 0 ? (byte) (var19 >> 2) : (byte) (var19 >> 2 ^ 192);
            val2 = (var20 & -128) == 0 ? (byte) (var20 >> 4) : (byte) (var20 >> 4 ^ 240);
            var16[var22] = LOOKUP_BASE64_ALPHABET[val1];
            var16[var22 + 1] = LOOKUP_BASE64_ALPHABET[val2 | var17 << 4];
            var16[var22 + 2] = LOOKUP_BASE64_ALPHABET[var18 << 2];
            var16[var22 + 3] = 61;
        }

        return var16;
    }

    public static byte[] decode(byte[] base64Data) {
        if (base64Data != null && base64Data.length != 0) {
            int numberQuadruple = base64Data.length / 4;
            Object decodedData = null;
            boolean b1 = false;
            boolean b2 = false;
            boolean b3 = false;
            boolean b4 = false;
            boolean marker0 = false;
            boolean marker1 = false;
            int encodedIndex = 0;
            boolean dataIndex = false;
            int i = base64Data.length;

            while (base64Data[i - 1] == 61) {
                --i;
                if (i == 0) {
                    return new byte[0];
                }
            }

            byte[] var12 = new byte[i - numberQuadruple];

            for (i = 0; i < numberQuadruple; ++i) {
                int var19 = i * 4;
                byte var17 = base64Data[var19 + 2];
                byte var18 = base64Data[var19 + 3];
                byte var13 = BASE64_ALPHABET[base64Data[var19]];
                byte var14 = BASE64_ALPHABET[base64Data[var19 + 1]];
                byte var15;
                if (var17 != 61 && var18 != 61) {
                    var15 = BASE64_ALPHABET[var17];
                    byte var16 = BASE64_ALPHABET[var18];
                    var12[encodedIndex] = (byte) (var13 << 2 | var14 >> 4);
                    var12[encodedIndex + 1] = (byte) ((var14 & 15) << 4 | var15 >> 2 & 15);
                    var12[encodedIndex + 2] = (byte) (var15 << 6 | var16);
                } else if (var17 == 61) {
                    var12[encodedIndex] = (byte) (var13 << 2 | var14 >> 4);
                } else if (var18 == 61) {
                    var15 = BASE64_ALPHABET[var17];
                    var12[encodedIndex] = (byte) (var13 << 2 | var14 >> 4);
                    var12[encodedIndex + 1] = (byte) ((var14 & 15) << 4 | var15 >> 2 & 15);
                }

                encodedIndex += 3;
            }

            return var12;
        } else {
            return new byte[0];
        }
    }

    public static String encodeBuffer(byte[] src) {
        byte[] aim = encode(src);
        StringBuffer sbuf = new StringBuffer("");

        for (int offset = 0; offset < aim.length; offset += 76) {
            int length = aim.length - offset > 76 ? 76 : aim.length - offset;

            try {
                sbuf.append(new String(aim, offset, length, "UTF-8")).append("\r\n");
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
            }
        }

        return sbuf.toString();
    }

    public static byte[] decodeBuffer(String src) {
        src = src.replace("\r", "");
        src = src.replace("\n", "");
        byte[] data = null;

        try {
            data = src.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        byte[] aim = decode(data);
        return aim;
    }

    public static String encodeBufferWithoutEnd(byte[] src) {
        byte[] aim = encode(src);
        StringBuffer sbuf = new StringBuffer("");

        for (int offset = 0; offset < aim.length; offset += 76) {
            int length = aim.length - offset > 76 ? 76 : aim.length - offset;

            try {
                sbuf.append(new String(aim, offset, length, "UTF-8"));
                if (length == 76) {
                    sbuf.append("\r\n");
                }
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
            }
        }

        return sbuf.toString();
    }
}
