package com.example.mymusic.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ArchiveName {
    public static String createName(String fileExtension){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String id = UUID.randomUUID().toString();
        return "recording" + timeStamp + "_" + id + "." + fileExtension;
    }

    public static void main(String[] args) {
        String AudioExtension = "mp3";
        String VideoExtension = "mp4";

        System.out.println("Name assigned: "+createName(AudioExtension));
    }
}
