package io.github.chrisbotcom.boomerang.votelistener;

import io.github.chrisbotcom.boomerang.Boomerang;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public final class Crypto {    
    private final String ALGORITHM = "RSA";
    private final String ENCODING = "UTF8";
    private final int KEYSIZE = 2048;
    private final String PUBLIC_FILENAME = "publicKey.pem"; 
    private final String PRIVATE_FILENAME = "privateKey.pem"; 

    private PublicKey publicKey;
    private PrivateKey privateKey;

    //@SuppressWarnings("anything")
    public Crypto(Boomerang plugin, File voteFolder) {        
        // When creating this object, if .pem files exist, their keys are loaded.
        // If keys do not already exist, they are generated and saved to their
        // repective .pem files.
        if (new File(voteFolder, PUBLIC_FILENAME).exists() && new File(voteFolder, PRIVATE_FILENAME).exists()) {
            try {
                publicKey = loadPublicKey(new File(voteFolder, PUBLIC_FILENAME).toURI().toURL());
                privateKey = loadPrivateKey(new File(voteFolder, PRIVATE_FILENAME).toURI().toURL());
                Logger.getLogger(Crypto.class.getName()).log(Level.INFO, null, "Crypto: Loaded keys from URLs!");
            } catch (URISyntaxException | IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, "Crypto: Could not load key pair!");
            }
        } else {
            KeyPair kp;
            try {
                kp = GenerateKeyPair();
            } catch (NoSuchAlgorithmException ex) {
                kp = null;
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, "Crypto: Could not generate key pair!");
                return;
            }
            
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
            
            try {
                saveKeyBytes(new File(voteFolder, PUBLIC_FILENAME).toURI().toURL(), publicKey.getEncoded());
                saveKeyBytes(new File(voteFolder, PRIVATE_FILENAME).toURI().toURL(), privateKey.getEncoded());
                Logger.getLogger(Crypto.class.getName()).log(Level.INFO, null, "Crypto: Generated and saved keys to URLs!");
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, "Crypto: Could not load key pair!");
            }
        }
    }

    private KeyPair GenerateKeyPair() throws NoSuchAlgorithmException {
        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        keyGen.initialize(KEYSIZE, random);
        return keyGen.generateKeyPair();
    }

    public PublicKey loadPublicKey(URL publicKeyUrl) throws URISyntaxException, FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = getKeyBytes(publicKeyUrl);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePublic(spec);
    }

    public PrivateKey loadPrivateKey(URL privateKeyUrl) throws URISyntaxException, FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = getKeyBytes(privateKeyUrl);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePrivate(spec);
    }

    public byte[] encrypt(String input) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException {
        byte[] cipherText;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipherText = cipher.doFinal(input.getBytes(ENCODING));
        return cipherText;
    }

    public byte[] decrypt(byte[] input) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        byte[] cipherText;
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipherText = cipher.doFinal(input);
        return cipherText;
    }

    private byte[] getKeyBytes(URL url) throws URISyntaxException, FileNotFoundException, IOException {
        File f = new File(url.toURI());
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        return keyBytes;
    }

    private void saveKeyBytes(URL url, byte[] b) throws URISyntaxException, FileNotFoundException, IOException {
        File f = new File(url.toURI());
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(b);
        fos.close();
    }
    
    public String getPublicKeyBase64String() {
        //byte[] encodedBytes = Base64.encodeBase64(publicKey.getEncoded());
        return DatatypeConverter.printBase64Binary(publicKey.getEncoded());
        //return new sun.misc.BASE64Encoder().encode(publicKey.getEncoded());
    }
}
