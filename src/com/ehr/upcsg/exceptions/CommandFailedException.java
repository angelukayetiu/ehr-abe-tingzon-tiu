/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehr.upcsg.exceptions;

/**
 *
 * @author angelukayetiu
 */
public class CommandFailedException extends Exception {

    /**
     * Creates a new instance of <code>CommandFailedException</code> without
     * detail message.
     */
    public CommandFailedException() {
    }

    /**
     * Constructs an instance of <code>CommandFailedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommandFailedException(String msg) {
        super(msg);
    }
}
