package com.shichko.deliveryservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RsaCipherUtilTest {

    @Test
    public void EncryptDecryptTest() {
        int id = 9;
        String c = RsaCipherUtil.encrypt(id);
        System.out.println(c);
        int decryptedId = RsaCipherUtil.decrypt(c);
        assertEquals(id, decryptedId);
    }

}