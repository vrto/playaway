package net.helpscout.playaway.web;

import net.helpscout.playaway.precondition.JsonError;

public interface JsonErrorBodyAware {
    JsonError createErrorBody();
}
