/*
,------.                       ,--.   ,--.       ,--.   ,--.,--.,--.  ,--.            
|  .---' ,--,--. ,---.,--. ,--.|   `.'   | ,---. |  |-. `--'|  |`--',-'  '-.,--. ,--. 
|  `--, ' ,-.  |(  .-' \  '  / |  |'.'|  || .-. || .-. ',--.|  |,--.'-.  .-' \  '  /  
|  `---.\ '-'  |.-'  `) \   '  |  |   |  |' '-' '| `-' ||  ||  ||  |  |  |    \   '   
`------' `--`--'`----'.-'  /   `--'   `--' `---'  `---' `--'`--'`--'  `--'  .-'  /    
                      `---'                                                 `---'     

    File: Hasher.java
    Date: 23 abr. 2023
  
    Authors: Félix Serna
 */
package em.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hasher {

    public static String getSHA512(byte[] _msg) {
        String hash = null;
        byte[] digest = null;
        if (_msg != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(_msg);
                digest = md.digest();
                hash = Base64.getEncoder().encodeToString(digest);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        } // if !null
        return hash;
    }

    public static String getSHA512(String _txt) {
        String hash = null;
        byte[] msg = null;
        byte[] digest = null;
        if (_txt != null) {
            msg = _txt.getBytes(StandardCharsets.UTF_16);
            hash = getSHA512(msg);
        } // if !null
        return hash;
    }

}
