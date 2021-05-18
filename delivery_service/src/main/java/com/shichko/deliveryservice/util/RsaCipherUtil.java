package com.shichko.deliveryservice.util;

import java.math.BigInteger;

public class RsaCipherUtil {
    private static final int q = 317;
    private static final int p = 463;
    private static final int n;
    private static final int fin;
    private static final int e = 5701;
    private static final int d = 56005;
    private static final int a = 12341;

    static {
        n = p * q;
        fin = (p - 1) * (q - 1);
    }

    private RsaCipherUtil() {

    }

    public static String encrypt(int num) {
        BigInteger bigNum = BigInteger.valueOf(num);
        BigInteger c = bigNum.modPow(BigInteger.valueOf(e), BigInteger.valueOf(n));
        return c.multiply(BigInteger.valueOf(a)).toString(16);
    }

    public static int decrypt(String enc) {
        BigInteger c = BigInteger.valueOf(Long.parseLong(enc, 16) / a);
        int m = c.modPow(BigInteger.valueOf(d), BigInteger.valueOf(n)).intValue();
        return m;
    }


}
