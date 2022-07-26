package ch.constructo.frontend.security;

import ch.constructo.backend.data.entities.User;

@FunctionalInterface
public interface CurrentUser {

  User getUser();
}

