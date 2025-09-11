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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class containing SonarQube issues for testing purposes.
 */
public class SonarQubeIssueGeneratorTest {

    // Issue: Constant names should comply with naming convention
    private static final String invalid_constant_name = "test";

    // Issue: Test method without assertions
    @Test
    public void testWithoutAssertions() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        generator.alwaysTrue();
        // No assertions
    }

    // Issue: Ignored test (Maintainability)
    @Ignore
    @Test
    public void ignoredTest() {
        Assert.assertTrue("This test is ignored", true);
    }

    // Issue: Hardcoded assertion message (Maintainability)
    @Test
    public void testWithHardcodedMessage() {
        Assert.assertEquals("The values should be equal", 1, 1);
    }

    // Issue: Thread.sleep in tests (Reliability)
    @Test
    public void testWithSleep() throws InterruptedException {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        Thread.sleep(1000); // Bad practice in tests
        Assert.assertTrue(generator.alwaysTrue());
    }

    // Issue: Assertion with literal boolean (Readability)
    @Test
    public void testAssertLiteralBoolean() {
        Assert.assertEquals(true, true);  // Should use assertTrue
    }

    // Issue: Test method with multiple assertions (Maintainability)
    @Test
    public void testMultipleAssertions() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        Assert.assertTrue(generator.alwaysTrue());
        Assert.assertEquals(10, generator.sumArray1(new int[]{5, 5}));
        Assert.assertEquals(15, generator.sumArray2(new int[]{5, 5, 5}));
        Assert.assertNotNull(generator);
        Assert.assertFalse(false);
    }

    // Issue: Assertion on exception message (Reliability)
    @Test
    public void testExceptionMessage() {
        try {
            throw new IllegalArgumentException("Error message");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Error message", e.getMessage());  // Fragile assertion
        }
    }

    // Issue: File operations in tests (Maintainability)
    @Test
    public void testFileOperations() throws IOException {
        File tempFile = new File("test.txt");
        tempFile.createNewFile();
        Assert.assertTrue(tempFile.exists());
        tempFile.delete();
    }

    // Issue: Using System.out in tests (Maintainability)
    @Test
    public void testWithSystemOut() {
        System.out.println("Test is running");
        Assert.assertTrue(true);
    }

    // Issue: Test method testing multiple concerns (Maintainability)
    @Test
    public void testMultipleConcerns() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();

        // Testing alwaysTrue
        Assert.assertTrue(generator.alwaysTrue());

        // Testing sumArray1
        Assert.assertEquals(10, generator.sumArray1(new int[]{5, 5}));

        // Testing inefficientCollections
        generator.inefficientCollections();

        // Testing equals
        Assert.assertTrue(generator.equals(new SonarQubeIssueGenerator()));
    }

    // Issue: Overly complex test method (Maintainability)
    @Test
    public void testComplexConditions() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();

        int result = generator.complexMethod(5, 4, 3, 2, 1);

        if (result > 10) {
            if (result < 20) {
                Assert.assertTrue(true);
            } else {
                Assert.assertFalse(false);
            }
        } else {
            if (result > 0) {
                Assert.assertTrue(true);
            } else {
                Assert.assertFalse(false);
            }
        }
    }

    // Issue: Random test (Reliability)
    @Test
    public void testWithRandomData() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        int random = generator.generateRandomPassword();
        Assert.assertTrue(random >= 0 && random < 10000);
    }

    // Issue: Empty test (Maintainability)
    @Test
    public void emptyTest() {
        // Empty test method
    }

    // Issue: Duplicated test code (Maintainability)
    @Test
    public void testDuplicate1() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        int[] array = {1, 2, 3, 4, 5};
        int sum = generator.sumArray1(array);
        Assert.assertEquals(15, sum);
    }

    // Issue: Duplicated test code (Maintainability)
    @Test
    public void testDuplicate2() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        int[] array = {1, 2, 3, 4, 5};
        int sum = generator.sumArray1(array);
        Assert.assertEquals(15, sum);
    }

    // Issue: Testing implementation details (Maintainability)
    @Test
    public void testImplementationDetails() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        // Testing private state or implementation details
        Assert.assertNotNull(generator.publicList);
        Assert.assertEquals(0, generator.publicList.size());
    }

    // Issue: Testing trivial code (Maintainability)
    @Test
    public void testTrivial() {
        List<String> list = Arrays.asList("a", "b", "c");
        Assert.assertEquals(3, list.size());
    }

    // Issue: Useless assertion (Maintainability)
    @Test
    public void testUselessAssertion() {
        Assert.assertNotNull(new Object());
    }

    // Issue: Test setup could be in @Before (Maintainability)
    @Test
    public void test1WithSetup() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        Assert.assertTrue(generator.alwaysTrue());
    }

    @Test
    public void test2WithSetup() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        Assert.assertEquals(15, generator.sumArray1(new int[]{5, 5, 5}));
    }

    @Test
    public void test3WithSetup() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        generator.inefficientCollections();
        Assert.assertTrue(true);
    }

    // Issue: JUnit 4 test in JUnit 5 project (or vice versa)
    @org.junit.jupiter.api.Test
    public void testMixedJUnitVersions() {
        Assert.assertTrue(true);
    }

    // Issue: Overlapping tests (Maintainability)
    @Test
    public void testSpecificCase() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        Assert.assertEquals(15, generator.sumArray1(new int[]{5, 5, 5}));
    }

    @Test
    public void testGeneralCase() {
        SonarQubeIssueGenerator generator = new SonarQubeIssueGenerator();
        // Tests the same functionality as testSpecificCase but with more cases
        Assert.assertEquals(15, generator.sumArray1(new int[]{5, 5, 5}));
        Assert.assertEquals(10, generator.sumArray1(new int[]{5, 5}));
        Assert.assertEquals(0, generator.sumArray1(new int[]{}));
    }

    // Issue: Assertion on wrong type (Reliability)
    @Test
    public void testAssertionWrongType() {
        Object obj = "string";
        Assert.assertTrue(obj instanceof String);  // Should use assertInstanceOf
    }
}
