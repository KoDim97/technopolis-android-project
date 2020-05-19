package com.example.technopolis.screens.authorization;

import androidx.annotation.NonNull;

public interface AuthorizationViewController {
    void logoSelected(final int pos);

    void enterBtnClick(@NonNull final String login, @NonNull final String password);

}
