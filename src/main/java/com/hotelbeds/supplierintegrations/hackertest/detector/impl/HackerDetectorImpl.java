package com.hotelbeds.supplierintegrations.hackertest.detector.impl;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;
import com.hotelbeds.supplierintegrations.hackertest.detector.model.Line;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HackerDetectorImpl implements HackerDetector {

    private static final Logger LOGGER = Logger.getLogger( HackerDetectorImpl.class.getName());

    List<Line> linesOrderByDate = new ArrayList<Line>();

    @Override
    public String parseLine(String line) {
        String[] lineSplited = line.split(",");
        if (lineSplited !=  null && lineSplited.length > 0) {
            if (lineSplited.length  == 4) {
                try{
                    Line lineObject = new Line(lineSplited);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(lineObject.getDate());
                    cal.add(Calendar.MINUTE, -5);
                    Date fiveMinutesAgo = cal.getTime();
                    if (!lineObject.getSignin()) {
                        //Checking last five minutes
                        Long ipLogsTimes = linesOrderByDate.stream().filter(
                                lineStream -> lineStream.getIp().equals(lineObject.getIp())
                                        && !lineStream.getSignin()
                                        && (lineStream.getDate().compareTo(fiveMinutesAgo) > 0)
                        ).count();
                        linesOrderByDate.add(lineObject);
                        this.cleaningListLines(lineObject, fiveMinutesAgo);
                        if (ipLogsTimes > 3) {
                            return lineObject.getIp();
                        } else{
                            return null;
                        }
                    } else {
                        //LOGIN OPERATION SUCCESS
                        this.cleaningListLines(lineObject, fiveMinutesAgo);
                        return null;
                    }
                }catch (IOException e) {
                    LOGGER.log( Level.WARNING, e.getMessage());
                }
            } else {
                LOGGER.log( Level.WARNING, "Line size is not correct");
            }
        } else {
            LOGGER.log( Level.WARNING, "Line is empty");
        }
        return null;
    }

    private void cleaningListLines(Line lineObject, Date fiveMinutesAgo){
        //Cleaning
        List<Line> linesBefore5min = linesOrderByDate.stream().filter(
                lineStream -> lineStream.getIp().equals(lineObject.getIp())
                        && !lineStream.getSignin()
                        && (lineStream.getDate().compareTo(fiveMinutesAgo) < 0)
        ).collect(Collectors.toList());

        linesOrderByDate.removeAll(linesBefore5min);
    }
}
