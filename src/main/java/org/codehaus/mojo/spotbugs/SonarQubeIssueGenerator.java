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
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class deliberately contains various SonarQube issues for testing purposes.
 * It includes issues related to:
 * - Security
 * - Maintainability
 * - Reliability
 * - Code Smells
 * - Intentionality
 */
public class SonarQubeIssueGenerator {

    // Issue: Public static field (Security - sensitive data exposure)
    public static String PASSWORD = "supersecretpassword123";

    // Issue: Unused private field (Maintainability)
    private int unusedField = 42;

    // Issue: Non-final static field (Security - concurrency issue)
    static Logger logger = Logger.getLogger(SonarQubeIssueGenerator.class.getName());

    // Issue: Magic number (Maintainability)
    private static final int TIMEOUT = 60000;

    // Issue: Unconditional if statement (Reliability)
    public boolean alwaysTrue() {
        if (true) {
            return true;
        }
        return false; // Unreachable code
    }

    // Issue: Empty catch block (Reliability)
    public void emptyCatch() {
        try {
            File file = new File("nonexistent.txt");
            FileInputStream fis = new FileInputStream(file);
        } catch (IOException e) {
            // This is empty on purpose
        }
    }

    // Issue: Duplicate code (Maintainability)
    public int sumArray1(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    // Issue: Duplicate code (Maintainability)
    public int sumArray2(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    // Issue: Unused method parameter (Maintainability)
    public void unusedParam(String unused, int used) {
        System.out.println("Used: " + used);
    }

    // Issue: SQL Injection vulnerability (Security)
    public void sqlInjection(String userInput) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "user", PASSWORD);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
        stmt.execute(query);
    }

    // Issue: Overly complex method (Maintainability)
    public int complexMethod(int a, int b, int c, int d, int e) {
        int result = 0;
        if (a > b) {
            if (b > c) {
                if (c > d) {
                    if (d > e) {
                        result = a + b + c + d + e;
                    } else {
                        result = a + b + c + d - e;
                    }
                } else {
                    if (c > e) {
                        result = a + b + c - d + e;
                    } else {
                        result = a + b + c - d - e;
                    }
                }
            } else {
                result = a + b - c;
            }
        } else {
            result = a - b;
        }
        return result;
    }

    // Issue: Inefficient use of collections (Performance)
    public void inefficientCollections() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(0, "item" + i); // Inefficient insertion at the beginning
        }
    }

    // Issue: Potential resource leak (Reliability)
    public void resourceLeak() throws IOException {
        FileInputStream fis = new FileInputStream("data.txt");
        byte[] data = new byte[1024];
        fis.read(data);
        // FileInputStream not closed
    }

    // Issue: Incorrect equals implementation (Reliability)
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SonarQubeIssueGenerator) {
            return true;
        }
        return false;
        // No hashCode() override
    }

    // Issue: Use of Random is not secure (Security)
    public int generateRandomPassword() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    // Issue: Serialization vulnerability (Security)
    public void deserializeObject() {
        try {
            FileInputStream fileIn = new FileInputStream("object.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Issue: Uninitialized collection (Reliability)
    private List<String> uninitializedList;

    public void useUninitializedList() {
        uninitializedList.add("This will cause NPE");
    }

    // Issue: Hardcoded IP address (Security)
    public void connectToHardcodedIP() {
        String ipAddress = "192.168.1.1";
        System.out.println("Connecting to " + ipAddress);
    }

    // Issue: Thread.sleep in loop (Performance)
    public void sleepInLoop() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Processing item " + i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Issue: Unused local variable (Maintainability)
    public void unusedLocalVar() {
        String unused = "This is never used";
        System.out.println("Hello World");
    }

    // Issue: Commented out code (Maintainability)
    public void commentedCode() {
        // The following code is commented out
        // if (true) {
        //     System.out.println("This should be removed");
        //     return;
        // }
    }

    // Issue: Too many parameters (Maintainability)
    public void tooManyParams(int a, int b, int c, int d, int e, int f, int g, int h) {
        System.out.println(a + b + c + d + e + f + g + h);
    }

    // Issue: System.exit (Reliability)
    public void exitSystem() {
        if (System.currentTimeMillis() % 2 == 0) {
            System.exit(1); // Abrupt termination
        }
    }

    // Issue: Use of deprecated method (Maintainability)
    @Deprecated
    public void deprecatedMethod() {
        System.out.println("This method is deprecated");
    }

    public void useDeprecatedMethod() {
        deprecatedMethod();
    }

    // Issue: Identical branches in if-else (Maintainability)
    public void identicalBranches(int value) {
        if (value > 0) {
            System.out.println("Processing value");
            doSomething();
        } else {
            System.out.println("Processing value");
            doSomething();
        }
    }

    private void doSomething() {
        // Do something
    }

    // Issue: Unnecessary toString() call (Maintainability)
    public void unnecessaryToString(String input) {
        System.out.println(input.toString());
    }

    // Issue: Boxed primitive just for toString (Performance)
    public String boxedPrimitiveToString(int value) {
        return new Integer(value).toString();
    }

    // Issue: Catching NPE instead of preventing it (Reliability)
    public void catchNPE(Object obj) {
        try {
            obj.toString();
        } catch (NullPointerException e) {
            System.out.println("Object was null");
        }
    }

    // Issue: Use of static collection (Security - thread safety)
    private static final Map<String, Object> CACHE = new HashMap<>();

    public void addToCache(String key, Object value) {
        CACHE.put(key, value); // Not thread-safe
    }

    // Issue: Overriding method with final (Maintainability)
    public final void overriddenMethod() {
        System.out.println("This is final but doesn't need to be");
    }

    // Issue: Empty static initializer (Maintainability)
    static {
        // This is empty
    }

    // Issue: Exception handling outside of main method
    static {
        try {
            // Some initialization
            File file = new File("config.txt");
            FileInputStream fis = new FileInputStream(file);
        } catch (Exception e) {
            // Bad practice to catch exceptions in static block
        }
    }

    // Issue: Large array declaration (Performance)
    private byte[] largeArray = new byte[100 * 1024 * 1024]; // 100MB array

    // Issue: Synchronization on non-final field (Concurrency)
    private Object lock = new Object();

    public void synchronizedMethod() {
        synchronized (lock) { // lock is not final
            System.out.println("This is synchronized");
        }
    }

    // Issue: Use of finalize method (Reliability)
    @Override
    protected void finalize() throws Throwable {
        // Use of finalize is discouraged
        super.finalize();
    }

    // Issue: Public non-static, non-final field (Encapsulation)
    public List<String> publicList = new ArrayList<>();

    // Issue: StringBuilder without initial capacity (Performance)
    public String inefficientStringBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("item").append(i).append(",");
        }
        return sb.toString();
    }

    // Issue: Use of local DateTime.now() (Testability)
    public boolean isWeekend() {
        int dayOfWeek = java.time.LocalDateTime.now().getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    // Issue: Use of instanceof in equals without class check (Reliability)
    public boolean badEquals(Object obj) {
        if (obj instanceof List) {
            return ((List<?>) obj).size() == 0;
        }
        return false;
    }

    // Issue: Creating new BigDecimal from double (Precision)
    public java.math.BigDecimal createBigDecimalFromDouble() {
        return new java.math.BigDecimal(0.1); // Should use String constructor
    }

    // Main method with various issues
    public static void main(String[] args) {
        // Issue: System.out for logging (Maintainability)
        System.out.println("Starting application...");

        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();

        // Issue: Direct use of command line arguments (Security)
        if (args.length > 0) {
            System.out.println("Processing: " + args[0]);
            try {
                generator.sqlInjection(args[0]);
            } catch (SQLException e) {
                e.printStackTrace(); // Issue: printStackTrace (Maintainability)
            }
        }

        // Issue: Hardcoded absolute path (Portability)
        File file = new File("C:\\temp\\data.txt");

        // Issue: Ignored return value (Reliability)
        file.delete();

        // Issue: Not closing resources in finally block (Reliability)
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("output.txt");
            fos.write("Hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Issue: Thread created but not started (Concurrency)
        Thread thread = new Thread(() -> {
            System.out.println("This thread is never started");
        });

        // Issue: Boxed primitive comparison using == (Reliability)
        Integer int1 = 128;
        Integer int2 = 128;
        if (int1 == int2) { // Should use equals()
            System.out.println("Equal integers");
        }
    }
}
