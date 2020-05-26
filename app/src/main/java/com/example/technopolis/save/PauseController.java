package com.example.technopolis.save;

public interface PauseController {
    /**
     * If authorized save information
     */
    void onPause();

    /**
     * Check saved authorization information
     * Download saved information if authorized and set authorized flag in app
     * Set not authorized in app if not authorized or when has error
     */
    void authorized();
}
