package com.commonsware.empublite;


import android.net.Uri;

import java.io.File;
import java.util.List;

public class BookContents {

    String title;
    List<BookContents.Chapter> chapters;
    File baseDir = null;

    static class Chapter {
        String file;
        String title;
    }

    int getChapterCount() {
        return chapters.size();
    }

    String getChapterFile(int position) {
        return chapters.get(position).file;
    }

    String getTitle() {
        return title;
    }

    void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    String getChapterPath(int position) {
        String file = getChapterFile(position);

        if (baseDir == null) {
            return ("file:///android_asset/book/" + file);
        }

        return Uri.fromFile(new File(baseDir, file)).toString();
    }

}
