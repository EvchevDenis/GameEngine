package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class EncryptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtils.class);

    private static final String ALGORITHM = "AES";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void encryptFile(File fileToEncrypt) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(Files.readAllBytes(fileToEncrypt.toPath()));

            File encryptedFile = new File(fileToEncrypt.getParent(), fileToEncrypt.getName() + ".encrypted");
            Files.write(encryptedFile.toPath(), Base64.getEncoder().encode(encryptedBytes));

            saveKeyHistory(fileToEncrypt.getName(), secretKey);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException | IllegalArgumentException | IOException e) {
            logger.error("Error: Encrypting level file.", e);
        }
    }

    public static void decryptFile(CustomFileChooser fileChooser, File fileToDecrypt, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM));

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(Files.readAllBytes(fileToDecrypt.toPath())));

            File decryptedFile = new File(fileToDecrypt.getParent(), fileToDecrypt.getName().replace(".encrypted", ""));
            Files.write(decryptedFile.toPath(), decryptedBytes);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException | IllegalArgumentException | IOException e) {
            logger.error("Error: Decrypting level file.", e);
            fileChooser.decryptionFailed();
        }
    }

    private static void saveKeyHistory(String fileName, SecretKey secretKey) {
        File keysFile = new File("levels/keys.txt");
        try (FileWriter writer = new FileWriter(keysFile, true)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            String dateTime = dateFormat.format(new Date());
            writer.write(fileName + " - " + Base64.getEncoder().encodeToString(secretKey.getEncoded()) + " - " + dateTime + "\n");
        } catch (IOException e) {
            logger.error("Error: Saving secret key history.", e);
        }
    }
}
