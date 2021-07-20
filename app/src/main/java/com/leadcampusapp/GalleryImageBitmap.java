package com.leadcampusapp;

import android.graphics.Bitmap;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shripad on 12-07-2018.
 */

public class GalleryImageBitmap {
    private static ArrayList<Bitmap> bitmapGalleryImage;
    private static ArrayList<String> document_slno;
    private static ArrayList<String> document_path;

    private static ArrayList<URL> allImgURL;

    public static ArrayList<URL> getAllImgURL() {
        return allImgURL;
    }

    public static void setAllImgURL(ArrayList<URL> allImgURL) {
        GalleryImageBitmap.allImgURL = allImgURL;
    }

    public static ArrayList<Bitmap> getBitmapGalleryImage() {
        return bitmapGalleryImage;
    }

    public static void setBitmapGalleryImage(ArrayList<Bitmap> bitmapGalleryImage) {
        GalleryImageBitmap.bitmapGalleryImage = bitmapGalleryImage;
    }

    public static ArrayList<String> getDocument_slno() {
        return document_slno;
    }

    public static void setDocument_slno(ArrayList<String> document_slno) {
        GalleryImageBitmap.document_slno = document_slno;
    }

    public static ArrayList<String> getDocument_path() {
        return document_path;
    }

    public static void setDocument_path(ArrayList<String> document_path) {
        GalleryImageBitmap.document_path = document_path;
    }

    /*    public ArrayList<Bitmap> getBitmapGalleryImage() {
        return bitmapGalleryImage;
    }

    public void setBitmapGalleryImage(ArrayList<Bitmap> bitmapGalleryImage) {
        this.bitmapGalleryImage = bitmapGalleryImage;
    }*/
}
