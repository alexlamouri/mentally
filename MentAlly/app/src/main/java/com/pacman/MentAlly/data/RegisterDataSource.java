package com.pacman.MentAlly.data;

import com.pacman.MentAlly.data.model.RegisteredUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ register credentials and retrieves user information.
 */
public class RegisterDataSource {

    public Result<RegisteredUser> register(String username, String password, String name,
                                           String DOB, String country) {

        try {
            // TODO: handle loggedInUser authentication
            RegisteredUser fakeUser =
                    new RegisteredUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error registering", e));
        }
    }


}
