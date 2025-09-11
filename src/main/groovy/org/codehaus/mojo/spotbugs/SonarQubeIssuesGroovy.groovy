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
package org.codehaus.mojo.spotbugs

import groovy.json.JsonSlurper
import groovy.xml.XmlParser
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

/**
 * This Groovy class deliberately contains various SonarQube issues for testing purposes.
 * It includes issues related to:
 * - Security
 * - Maintainability
 * - Reliability
 * - Code Smells
 * - Intentionality
 */
class SonarQubeIssuesGroovy {

    // Issue: Public static field with sensitive data (Security)
    public static String API_KEY = "1234567890abcdef"

    // Issue: Hardcoded credentials (Security)
    private static final String DB_USERNAME = "admin"
    private static final String DB_PASSWORD = "admin123"

    // Issue: Non-final static field (Concurrency)
    static Map<String, Object> cache = new HashMap<>()

    // Issue: Non-thread-safe collection (Concurrency)
    static ArrayList<String> sharedList = new ArrayList<>()

    // Issue: Inefficient String concatenation in loop (Performance)
    def buildLargeString() {
        String result = ""
        for (i in 1..1000) {
            result += "Item " + i + ", "
        }
        return result
    }

    // Issue: Empty catch block (Reliability)
    def readFile(String path) {
        try {
            return new File(path).text
        } catch (Exception e) {
            // Empty catch block
        }
    }

    // Issue: SQL Injection (Security)
    def executeQuery(String userInput) {
        def sql = groovy.sql.Sql.newInstance("jdbc:mysql://localhost:3306/test", DB_USERNAME, DB_PASSWORD, "com.mysql.jdbc.Driver")
        sql.execute("SELECT * FROM users WHERE name = '${userInput}'")
        sql.close()
    }

    // Issue: Unreachable code (Maintainability)
    def unreachableCode() {
        return "Early return"
        println "This will never be executed"
    }

    // Issue: Collection not cleared (Reliability)
    def processItems() {
        def items = new ArrayList<String>()
        // Fill the list
        for (i in 1..1000) {
            items.add("Item $i")
        }
        // Process items
        for (item in items) {
            println item
        }
        // Items not cleared, potentially causing memory issues
    }

    // Issue: Field name hides parent class field (Maintainability)
    String toString = "Incorrect toString override"

    // Issue: Not using try-with-resources (Resource leak)
    def writeToFile(String content) {
        def writer = new FileWriter("output.txt")
        writer.write(content)
        // writer not closed
    }

    // Issue: Inefficient regular expression (Performance)
    def validateEmail(String email) {
        return email =~ /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
    }

    // Issue: Using Thread.sleep (Performance)
    def sleepInLoop() {
        for (i in 1..10) {
            Thread.sleep(1000)
            println "Processing item $i"
        }
    }

    // Issue: Method with too many parameters (Maintainability)
    def complexMethod(param1, param2, param3, param4, param5, param6, param7, param8) {
        return param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8
    }

    // Issue: Non-synchronized access to static field (Concurrency)
    def addToCache(String key, Object value) {
        cache.put(key, value)
    }

    // Issue: Catching general Exception (Reliability)
    def parseJson(String content) {
        try {
            def jsonSlurper = new JsonSlurper()
            return jsonSlurper.parseText(content)
        } catch (Exception e) {
            // Catching all exceptions is bad practice
            return [:]
        }
    }

    // Issue: Inefficient use of streams (Performance)
    def readLargeFile(String filePath) {
        def lines = Files.lines(Paths.get(filePath))
        def count = 0
        lines.forEach { line ->
            count++
        }
        // Stream not closed
        return count
    }

    // Issue: Ignored return value (Reliability)
    def deleteFile() {
        new File("temp.txt").delete()
        // Return value of delete() is ignored
    }

    // Issue: System.out.println for logging (Maintainability)
    def logMessage(String message) {
        System.out.println("LOG: " + message)
    }

    // Issue: Use of eval (Security)
    def evaluateCode(String code) {
        Eval.me(code)
    }

    // Issue: Manual resource management (Reliability)
    def processDatabase() {
        Connection conn = null
        Statement stmt = null
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", DB_USERNAME, DB_PASSWORD)
            stmt = conn.createStatement()
            stmt.execute("SELECT * FROM users")
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            // Incorrect closing order
            if (conn != null) {
                conn.close()
            }
            if (stmt != null) {
                stmt.close()
            }
        }
    }

    // Issue: Excessive method length (Maintainability)
    def veryLongMethod() {
        // Imagine this method has 100+ lines of code
        println "Line 1"
        println "Line 2"
        println "Line 3"
        println "Line 4"
        println "Line 5"
        println "Line 6"
        println "Line 7"
        println "Line 8"
        println "Line 9"
        println "Line 10"
        println "Line 11"
        println "Line 12"
        println "Line 13"
        println "Line 14"
        println "Line 15"
        println "Line 16"
        println "Line 17"
        println "Line 18"
        println "Line 19"
        println "Line 20"
        // And so on...
    }

    // Issue: Potential NPE (Reliability)
    def processString(String input) {
        return input.toUpperCase() // No null check
    }

    // Issue: Boxed primitive comparison (Reliability)
    def compareIntegers(Integer a, Integer b) {
        return a == b // Should use equals()
    }

    // Issue: Dead code (Maintainability)
    def deadCode() {
        if (false) {
            println "This will never be executed"
        }
    }

    // Issue: Commented out code (Maintainability)
    def commentedCode() {
        // def oldMethod() {
        //     println "This was commented out instead of deleted"
        // }
    }

    // Issue: Redundant if statement (Maintainability)
    def redundantIf(boolean condition) {
        if (condition) {
            return true
        } else {
            return false
        }
    }

    // Issue: Vulnerable XML parser (Security)
    def parseXml(String xml) {
        def parser = new XmlParser(false, false)
        return parser.parseText(xml)
    }

    // Issue: Inefficient list initialization (Performance)
    def createLargeList() {
        def list = []
        for (i in 1..10000) {
            list << i
        }
        return list
    }

    // Issue: Empty method (Maintainability)
    def emptyMethod() {
        // Empty method
    }

    // Issue: Too complex Boolean expression (Maintainability)
    def complexCondition(a, b, c, d, e) {
        return (a && b) || (c && d) || (a && e) || (b && c && d) || (a && c && e)
    }

    // Issue: Main method with issues
    static void main(String[] args) {
        def instance = new SonarQubeIssuesGroovy()

        // Issue: Direct use of command line arguments
        if (args.length > 0) {
            instance.executeQuery(args[0])
        }

        // Issue: Printing stacktrace
        try {
            instance.readFile("/nonexistent/path")
        } catch (Exception e) {
            e.printStackTrace()
        }

        // Issue: Thread without start
        def thread = new Thread({
            println "This thread is never started"
        })

        // Issue: System.exit
        if (new Random().nextBoolean()) {
            System.exit(1)
        }

        // Issue: Busy wait
        def flag = false
        while (!flag) {
            // This is a busy wait
            Thread.sleep(100)
        }
    }
}
