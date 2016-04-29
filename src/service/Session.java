/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pc
 */
public class Session {

    private static HashMap<Object, String> attributes = new HashMap<>();

    // pour ajouter une variable de session
    public static void setAttributes(Object myObject, String myString) {
        attributes.put(myObject, myString);
    }

    // pour recupere une variable de session
    public static Object getAttributes(String myString) {
        for (Map.Entry<Object, String> entry : attributes.entrySet()) {
            Object object = entry.getKey();
            String string = entry.getValue();
            if (string.equals(myString)) {
                return object;
            }
        }
        return null;
    }

}
