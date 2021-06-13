package com.hotelbeds.supplierintegrations.hackertest.detector.model;

import lombok.Getter;
import lombok.Setter;
import utils.Utils;

import java.io.IOException;
import java.util.Date;

@Getter
@Setter
public class Line {

    String ip;
    Date date;
    Boolean signin;
    String name;

    public Line(String[] lineArray) throws IOException {
        ip = lineArray[0];
        date = Utils.epochToDate(lineArray[1].toString());
        signin = lineArray[2].toString().contains("SUCCESS") ? true:false;
        name = lineArray[3];
    }
}
