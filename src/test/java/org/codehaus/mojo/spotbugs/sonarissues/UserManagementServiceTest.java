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
package org.codehaus.mojo.spotbugs.sonarissues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the UserManagementService class.
 */
public class UserManagementServiceTest {

    private UserManagementService service;

    @BeforeEach
    public void setUp() {
        service = new UserManagementService();
    }

    @Test
    public void testValidateUserSession() {
        boolean result = service.validateUserSession("test-session");
        assertFalse(result, "New session should not be active");
    }

    @Test
    public void testCreateUserUploadDirectory() throws Exception {
        File dir = service.createUserUploadDirectory("test-user");
        assertTrue(dir.exists(), "Directory should be created");
        assertTrue(dir.isDirectory(), "Should be a directory");
        dir.delete(); // Clean up
    }

    @Test
    public void testIsSameAccountBalance() {
        AtomicLong balance1 = new AtomicLong(100);
        AtomicLong balance2 = new AtomicLong(100);

        // These are different instances with the same value
        assertFalse(service.isSameAccountBalance(balance1, balance2),
                   "Should return false for different instances");

        // Same instance should return true
        assertTrue(service.isSameAccountBalance(balance1, balance1),
                  "Should return true for same instance");
    }

    @Test
    public void testCalculateSubscriptionFee() {
        BigDecimal fee = service.calculateSubscriptionFee(100.0, 0.1);
        assertEquals(new BigDecimal("110.0"), fee, "Fee should include tax");
    }

    @Test
    public void testCompareUserDisplayNames() {
        String name1 = "John";
        String name2 = new String("John"); // Creates a different instance with the same value

        assertFalse(service.compareUserDisplayNames(name1, name2),
                   "Should return false for different instances");
    }

    @Test
    public void testHasValidUsernamePrefix() {
        String validUsername = "admin_user_123";
        assertTrue(service.hasValidUsernamePrefix(validUsername),
                  "Username should have valid prefix");

        String invalidUsername = "user_123";
        assertFalse(service.hasValidUsernamePrefix(invalidUsername),
                   "Username with prefix at start should return false");
    }

    @Test
    public void testClone() {
        UserManagementService cloned = service.clone();
        assertNotNull(cloned, "Cloned object should not be null");
    }
}
