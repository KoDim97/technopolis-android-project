package com.example.technopolis.screens.authorization;

import androidx.annotation.NonNull;

public interface AuthorizationViewController {
    /**
     * Set type of education project in api
     *
     * @param pos - index of education project
     */
    void logoSelected(final int pos);

    /**
     * Try to authorized with this login and password
     * Set authorized flag in app in case of success
     * else show toast with message
     */
    void enterBtnClick(@NonNull final String login, @NonNull final String password);

}
