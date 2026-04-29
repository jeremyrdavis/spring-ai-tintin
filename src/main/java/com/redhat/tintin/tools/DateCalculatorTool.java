package com.redhat.tintin.tools;

import org.springframework.ai.tool.annotation.Tool;

import java.time.Year;

public class DateCalculatorTool {

    @Tool(description = "Calculate how many years ago a given year was")
    public int yearsAgo(int year) {
        return Year.now().getValue() - year;
    }

    @Tool(description = "Calculate the time span in years between two given years")
    public int yearsBetween(int startYear, int endYear) {
        return endYear - startYear;
    }
}
