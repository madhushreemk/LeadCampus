package com.leadcampusapp;

import android.graphics.Bitmap;

/**
 * Created by Shripad on 26-07-2018.
 */

public class StudentPMImageBitmap {
    public static Bitmap studBitmap;
    public static Bitmap pmBitmap;

    public static byte[] studByteArray;
    public static byte[] pmByteArray;

    public static byte[] getStudByteArray() {
        return studByteArray;
    }

    public static void setStudByteArray(byte[] studByteArray) {
        StudentPMImageBitmap.studByteArray = studByteArray;
    }

    public static byte[] getPmByteArray() {
        return pmByteArray;
    }

    public static void setPmByteArray(byte[] pmByteArray) {
        StudentPMImageBitmap.pmByteArray = pmByteArray;
    }

    public static Bitmap getStudBitmap() {
        return studBitmap;
    }

    public static void setStudBitmap(Bitmap studBitmap) {
        StudentPMImageBitmap.studBitmap = studBitmap;
    }

    public static Bitmap getPmBitmap() {
        return pmBitmap;
    }

    public static void setPmBitmap(Bitmap pmBitmap) {
        StudentPMImageBitmap.pmBitmap = pmBitmap;
    }
}
