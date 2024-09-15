package debugger.app.shclient;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
//import android.support.annotation.RequiresApi;

import androidx.annotation.RequiresApi;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.MqttAndroidClient;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Sender {
    private Context context;
    private MqttAndroidClient client;



    public Sender(Context context) {
        this.context = context;
    }

    public void setConnect(String add) throws MqttException {
        //add = "tcp://192.168.43.177:1883";
        add = "tcp://192.168.0.17:1883";
        //add = "tcp://192.168.1.100:1883";
        client = new MqttAndroidClient(context, add, "test1");

        client.connect().setActionCallback(new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken iMqttToken) {
                System.out.println("success connect mqtt");
            }

            @Override
            public void onFailure(IMqttToken iMqttToken, Throwable throwable) {

            }
        });
    }

    private static String stringToHex(String input) {
        char[] characters = input.toCharArray();
        StringBuilder hexString = new StringBuilder();

        for (char c : characters) {
            int intValue = (int) c;
            String hexValue = Integer.toHexString(intValue);
            hexString.append('0');
            hexString.append('x');
            hexString.append(hexValue.toUpperCase(Locale.ROOT));
            hexString.append(',');
        }

        return hexString.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void SendMessage(MqttAndroidClient byClient, String message) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
//
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        System.out.println(stringToHex(privateKey.toString()));
//
//        byte[] encryptedMessage = encrypt(message, publicKey);
//        String base64EncryptedMessage = Base64.getEncoder().encodeToString(encryptedMessage);
//


        String key = "0123456789abcdef0123456789abcdef";
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        @SuppressLint("GetInstance") Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());

        System.out.println(Arrays.toString(encryptedBytes));

        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println(stringToHex(encryptedText));

        String topic = "test";
        int qos = 1;
        try {
            //byClient.publish(topic, encryptedText.getBytes(), qos, true);
            byClient.publish(topic, message.getBytes(), qos, true);
        }
        catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private static byte[] encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public MqttAndroidClient getClient(){
        return client;
    }
}
