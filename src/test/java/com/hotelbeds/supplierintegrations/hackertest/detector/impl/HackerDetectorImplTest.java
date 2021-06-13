package com.hotelbeds.supplierintegrations.hackertest.detector.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackerDetectorImpl.class)
class HackerDetectorImplTest {

    @Autowired
    HackerDetectorImpl hackerDetector;

    @Test
    /**
     * Finding one IP suspicious in a logs file example(testSuspiciousFound.txt)
     */
    public void parseLineTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/logs/testSuspiciousFound.txt"));
        String ipSuspicous = null;
        for (int i = 0; i < 9; i++) {
            String currentLine = reader.readLine();
            ipSuspicous= hackerDetector.parseLine(currentLine);
            if (i != 8){
                assertEquals(ipSuspicous, null);
            } else{
                assertEquals(ipSuspicous, "80.238.9.179");
            }
        }
        reader.close();
    }

    @Test
    /**
     * Checking list saved regiters to check the cleaner to avoid memory problems
     */
    public void parseLineCleanerTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/logs/testCleaner.txt"));
        for (int i = 0; i < 18; i++) {
            String currentLine = reader.readLine();
            hackerDetector.parseLine(currentLine);
        }
        reader.close();
        assertEquals(hackerDetector.linesOrderByDate.size(), 2);
    }


}