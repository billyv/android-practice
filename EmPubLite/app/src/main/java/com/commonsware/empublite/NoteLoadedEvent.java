package com.commonsware.empublite;

/**
 * Created by Billy on 2015-03-06.
 */
public class NoteLoadedEvent {

    int position;
    String prose;

    NoteLoadedEvent(int position, String prose) {
        this.position = position;
        this.prose = prose;
    }

    int getPosition() {
        return position;
    }

    String getProse() {
        return prose;
    }

}
