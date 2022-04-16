package com.example.finalproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class issuesList {

    public static File theIssueList() throws IOException {
        File issues = new File("issuesList.txt");
        FileWriter writer = new FileWriter("issuesList.txt");

        writer.write("This is the issues list");
        writer.close();

        return issues;
    }
    public static void main(String[] args) throws IOException {
        theIssueList();
    }
}