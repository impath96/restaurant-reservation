package com.zerobase.reservation.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class Aes256UtilsTest {

    @Test
    void encryptAndDecryptTest() {

        String text = "zerobase";

        String encryptedText = Aes256Utils.encrypt(text);

        assertThat(text).isEqualTo(Aes256Utils.decrypt(encryptedText));
    }
}