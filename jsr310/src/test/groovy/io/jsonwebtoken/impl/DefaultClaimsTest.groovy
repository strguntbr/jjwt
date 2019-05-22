/*
 * Copyright (C) 2015 jsonwebtoken.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jsonwebtoken.impl

import io.jsonwebtoken.Claims
import org.junit.Before
import org.junit.Test

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

import static org.junit.Assert.assertEquals

class DefaultClaimsTest {

    Claims claims

    @Before
    void setup() {
        claims = new DefaultClaims()
    }

    @Test
    void testGetClaimWithRequiredType_Instant_Success() {
        def actual = Instant.now()
        claims.put("aDate", actual)
        Instant expected = claims.get("aDate", Instant.class)
        assertEquals(expected, actual)
    }

    @Test
    void testGetClaimExpiration_Instant_Success() {
        def now = Instant.now()
        claims.setExpiration(now)
        Instant expected = claims.get("exp", Instant.class)
        assertEquals(expected, claims.getExpiration(Instant.class))
    }

    @Test
    void testGetClaimIssuedAt_Instant_Success() {
        def now = Instant.now()
        claims.setIssuedAt(now)
        Instant expected = claims.get("iat", Instant.class)
        assertEquals(expected, claims.getIssuedAt(Instant.class))
    }

    @Test
    void testGetClaimNotBefore_Instant_Success() {
        def now = Instant.now()
        claims.setNotBefore(now)
        Instant expected = claims.get("nbf", Instant.class)
        assertEquals(expected, claims.getNotBefore(Instant.class))
    }

    @Test
    void testPutWithIat_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.put('iat', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutAllWithIat_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.putAll([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testConstructorWithIat_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        this.claims = new DefaultClaims([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutWithNbf_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.put('nbf', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutAllWithNbf_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.putAll([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testConstructorWithNbf_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        this.claims = new DefaultClaims([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutWithExp_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.put('exp', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutAllWithExp_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.putAll([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testConstructorWithExp_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        this.claims = new DefaultClaims([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutWithNonSpecDate_Instant() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        Instant now = Instant.ofEpochSecond(seconds)
        claims.put('foo', now)
        assertEquals now, claims.get('foo') //conversion should NOT have occurred
    }
    /*************/

    @Test
    void testGetClaimWithRequiredType_OffsetDateTime_Success() {
        def actual = OffsetDateTime.now()
        claims.put("aDate", actual)
        OffsetDateTime expected = claims.get("aDate", OffsetDateTime.class)
        assertEquals(expected, actual)
    }

    @Test
    void testGetClaimExpiration_OffsetDateTime_Success() {
        def now = OffsetDateTime.now()
        claims.setExpiration(now)
        OffsetDateTime expected = claims.get("exp", OffsetDateTime.class)
        assertEquals(expected, claims.getExpiration(OffsetDateTime.class))
    }

    @Test
    void testGetClaimIssuedAt_OffsetDateTime_Success() {
        def now = OffsetDateTime.now()
        claims.setIssuedAt(now)
        OffsetDateTime expected = claims.get("iat", OffsetDateTime.class)
        assertEquals(expected, claims.getIssuedAt(OffsetDateTime.class))
    }

    @Test
    void testGetClaimNotBefore_OffsetDateTime_Success() {
        def now = OffsetDateTime.now()
        claims.setNotBefore(now)
        OffsetDateTime expected = claims.get("nbf", OffsetDateTime.class)
        assertEquals(expected, claims.getNotBefore(OffsetDateTime.class))
    }

    @Test
    void testPutWithIat_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('iat', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutAllWithIat_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testConstructorWithIat_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutWithNbf_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('nbf', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutAllWithNbf_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testConstructorWithNbf_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutWithExp_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('exp', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutAllWithExp_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testConstructorWithExp_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutWithNonSpecDate_OffsetDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        OffsetDateTime now = OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('foo', now)
        assertEquals now, claims.get('foo') //conversion should NOT have occurred
    }
/*******/

    @Test
    void testGetClaimWithRequiredType_ZonedDateTime_Success() {
        def actual = ZonedDateTime.now()
        claims.put("aDate", actual)
        ZonedDateTime expected = claims.get("aDate", ZonedDateTime.class)
        assertEquals(expected, actual)
    }

    @Test
    void testGetClaimExpiration_ZonedDateTime_Success() {
        def now = ZonedDateTime.now()
        claims.setExpiration(now)
        ZonedDateTime expected = claims.get("exp", ZonedDateTime.class)
        assertEquals(expected, claims.getExpiration(ZonedDateTime.class))
    }

    @Test
    void testGetClaimIssuedAt_ZonedDateTime_Success() {
        def now = ZonedDateTime.now()
        claims.setIssuedAt(now)
        ZonedDateTime expected = claims.get("iat", ZonedDateTime.class)
        assertEquals(expected, claims.getIssuedAt(ZonedDateTime.class))
    }

    @Test
    void testGetClaimNotBefore_ZonedDateTime_Success() {
        def now = ZonedDateTime.now()
        claims.setNotBefore(now)
        ZonedDateTime expected = claims.get("nbf", ZonedDateTime.class)
        assertEquals(expected, claims.getNotBefore(ZonedDateTime.class))
    }

    @Test
    void testPutWithIat_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('iat', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutAllWithIat_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testConstructorWithIat_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([iat: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('iat') //conversion should have happened
    }

    @Test
    void testPutWithNbf_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('nbf', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutAllWithNbf_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testConstructorWithNbf_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([nbf: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('nbf') //conversion should have happened
    }

    @Test
    void testPutWithExp_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('exp', now) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutAllWithExp_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.putAll([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testConstructorWithExp_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        this.claims = new DefaultClaims([exp: now]) //this should convert 'now' to seconds since epoch
        assertEquals seconds, claims.get('exp') //conversion should have happened
    }

    @Test
    void testPutWithNonSpecDate_ZonedDateTime() {
        long millis = System.currentTimeMillis()
        long seconds = millis / 1000 as long
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault())
        claims.put('foo', now)
        assertEquals now, claims.get('foo') //conversion should NOT have occurred
    }

}
