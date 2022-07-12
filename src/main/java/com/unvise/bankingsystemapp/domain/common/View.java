package com.unvise.bankingsystemapp.domain.common;

/**
 * A class that serves to define the fields in the Dto class that you want to fail.
 * Specified in the 'groups' parameter of the validation annotations.
 *
 * @author unvise
 * @version 0.1
 */
public final class View {

    /**
     * Used when saving new data.
     * Should be used when validating data.
     */
    public interface New {
    }


    /**
     * Used when updating existing data.
     * Should be used when validating data.
     */
    public interface Update {
    }

}
