package com.leadcampusapp;

import java.util.ArrayList;

public class CompletionProjectStaticClass {
    private static int countCompletion=0;
    private static ArrayList<String> fileArrayString;
    private static ArrayList<String> fileDocString;
    private static int countDocumentCompletion=0;

    private static ArrayList<byte[]> byteArrayImage;  // by madhu
    private static byte[] bytesImage;  //by madhu

    public static byte[] getBytesImage() {
        return bytesImage;
    }

    public static void setBytesImage(byte[] bytesImage) {
        CompletionProjectStaticClass.bytesImage = bytesImage;
    }

    public static ArrayList<byte[]> getByteArrayImage() {
        return byteArrayImage;
    }

    public static void setByteArrayImage(ArrayList<byte[]> byteArrayImage) {
        CompletionProjectStaticClass.byteArrayImage = byteArrayImage;
    }

    public static int getCountDocumentCompletion() {
        return countDocumentCompletion;
    }

    public static void setCountDocumentCompletion(int countDocumentCompletion) {
        CompletionProjectStaticClass.countDocumentCompletion = countDocumentCompletion;
    }

    public static ArrayList<String> getFileDocString() {
        return fileDocString;
    }

    public static void setFileDocString(ArrayList<String> fileDocString) {
        CompletionProjectStaticClass.fileDocString = fileDocString;
    }

    public static ArrayList<String> getFileArrayString() {
        return fileArrayString;
    }

    public static void setFileArrayString(ArrayList<String> fileArrayString) {
        CompletionProjectStaticClass.fileArrayString = fileArrayString;
    }

    public static int getCountCompletion() {
        return countCompletion;
    }

    public static void setCountCompletion(int countCompletion) {
        CompletionProjectStaticClass.countCompletion = countCompletion;
    }
}
