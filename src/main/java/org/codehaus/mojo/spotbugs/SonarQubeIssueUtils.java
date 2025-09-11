/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.mojo.spotbugs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Utility class with various SonarQube issues.
 */
public class SonarQubeIssueUtils {

    // Issue: Public static mutable field (Security)
    public static Properties CONFIG = new Properties();

    // Issue: Unused private method (Maintainability)
    private static void unusedMethod() {
        System.out.println("This method is never called");
    }

    // Issue: Hardcoded IP address (Security)
    private static final String SERVER_IP = "192.168.1.100";
    private static final int SERVER_PORT = 8080;

    // Issue: Insecure cipher (Security)
    public static String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("DES"); // DES is insecure
        return new String(cipher.doFinal(password.getBytes()));
    }

    // Issue: Weak hashing algorithm (Security)
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5"); // MD5 is weak
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Issue: XML parsing vulnerability (Security)
    public static org.w3c.dom.Document parseXml(String xmlFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Missing: dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new File(xmlFile));
    }

    // Issue: Inefficient file copy (Performance)
    public static void copyFile(String source, String destination) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        // Should use Files.copy instead
    }

    // Issue: Socket not closed in finally block (Reliability)
    public static String sendRequest(String data) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        OutputStream out = socket.getOutputStream();
        out.write(data.getBytes());

        InputStream in = socket.getInputStream();
        byte[] response = new byte[1024];
        in.read(response);

        socket.close(); // Should be in finally block
        return new String(response);
    }

    // Issue: Non-thread-safe lazy initialization (Concurrency)
    private static Map<String, Object> cache;

    public static Map<String, Object> getCache() {
        if (cache == null) {
            cache = new HashMap<>(); // Not thread-safe
        }
        return cache;
    }

    // Issue: Thread-safe alternative with double-checked locking but without volatile (Concurrency)
    private static Map<String, Object> betterCache;

    public static Map<String, Object> getBetterCache() {
        if (betterCache == null) { // First check (not thread-safe without volatile)
            synchronized (SonarQubeIssueUtils.class) {
                if (betterCache == null) { // Second check
                    betterCache = new HashMap<>();
                }
            }
        }
        return betterCache;
    }

    // Issue: Using default encoding (Internationalization)
    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded); // Uses default encoding instead of specifying one
    }

    // Issue: Potential path traversal (Security)
    public static File getFile(String fileName) {
        return new File("/tmp/" + fileName); // No validation of fileName
    }

    // Issue: Exception swallowing (Reliability)
    public static void deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            // Exception swallowed
        }
    }

    // Issue: Creating temp files with predictable names (Security)
    public static File createTempFile() throws IOException {
        File temp = new File("/tmp/temp_" + System.currentTimeMillis() + ".txt");
        temp.createNewFile();
        return temp;
    }

    // Issue: Method too complex (Maintainability)
    public static int calculateComplexValue(int[] values) {
        int result = 0;
        boolean foundNegative = false;
        boolean foundPositive = false;
        boolean foundZero = false;

        for (int i = 0; i < values.length; i++) {
            if (values[i] < 0) {
                foundNegative = true;
                if (values[i] < -10) {
                    result += values[i] * 2;
                } else {
                    result += values[i];
                }
            } else if (values[i] > 0) {
                foundPositive = true;
                if (values[i] > 10) {
                    result += values[i] * 2;
                } else {
                    result += values[i];
                }
            } else {
                foundZero = true;
            }
        }

        if (foundNegative && foundPositive) {
            result *= 2;
        } else if (foundZero) {
            result += 10;
        }

        return result;
    }

    // Issue: Method with side effects (Maintainability)
    private static int counter = 0;

    public static boolean checkAndIncrement(int value) {
        counter++; // Side effect
        return value > counter;
    }

    // Issue: Integer division without checking for divide by zero (Reliability)
    public static int divide(int a, int b) {
        return a / b; // No check for b == 0
    }

    // Issue: Non-short-circuit logic (Intentionality)
    public static boolean checkValues(List<String> values, int index) {
        return index >= 0 & values.get(index).length() > 0; // Should use &&
    }

    // Issue: Inefficient string concatenation in loop (Performance)
    public static String buildString(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result = result + "Item " + i; // Inefficient
        }
        return result;
    }

    // Issue: Catch exceptions in the wrong order (Reliability)
    public static void processFile(String path) {
        try {
            Files.readAllBytes(Paths.get(path));
        } catch (Exception e) { // Catches all exceptions
            System.err.println("General error: " + e.getMessage());
        } catch (IOException e) { // This will never be reached
            System.err.println("IO error: " + e.getMessage());
        }
    }

    // Issue: Non-serializable class with serializable field (Reliability)
    private static class NonSerializable {
        private java.util.Date date = new java.util.Date();
    }

    // Issue: Return value of method ignored (Reliability)
    public static void renameFile(String oldName, String newName) {
        new File(oldName).renameTo(new File(newName)); // Return value ignored
    }

    // Issue: Exposing internal representation (Encapsulation)
    private static String[] internalArray = {"one", "two", "three"};

    public static String[] getInternalArray() {
        return internalArray; // Should return a copy
    }

    // Issue: Using Random for security purposes (Security)
    public static String generateToken() {
        Random random = new Random();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            token.append(Integer.toHexString(random.nextInt(16)));
        }
        return token.toString();
    }

    // Issue: Inefficient List instantiation (Performance)
    public static List<Integer> createList(int size) {
        ArrayList<Integer> list = new ArrayList<>(); // No initial capacity
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    // Issue: Using ConcurrentHashMap incorrectly (Concurrency)
    private static final Map<String, Object> concurrentMap = new ConcurrentHashMap<>();

    public static void updateMap(String key, Object value) {
        Object oldValue = concurrentMap.get(key);
        if (oldValue == null) {
            concurrentMap.put(key, value); // Race condition: check-then-act
        }
    }

    // Issue: Unchecked warning suppression (Type safety)
    @SuppressWarnings("unchecked")
    public static <T> T unsafeCast(Object obj) {
        return (T) obj;
    }

    // Issue: Comparison of floating point numbers (Precision)
    public static boolean areEqual(double a, double b) {
        return a == b; // Should use epsilon comparison
    }

    // Issue: main method with lots of issues
    public static void main(String[] args) {
        try {
            // Issue: Command line arguments without validation (Security)
            if (args.length > 0) {
                String path = args[0];
                File file = new File(path);

                // Issue: Unsafe file operations
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close(); // Not in finally

                    // Issue: Printing sensitive data
                    System.out.println(new String(data));
                }
            }

            // Issue: Creating thread without start
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread running");
                }
            });
            // thread.start(); // Commented out, thread never started

            // Issue: Calling System.exit (Reliability)
            if (new Random().nextBoolean()) {
                System.exit(0);
            }
        } catch (Exception e) {
            // Issue: printStackTrace (Maintainability)
            e.printStackTrace();
        }
    }
}
