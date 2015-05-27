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
public class SSLClientErrorException extends Exception {

    /**
     * Creates a new instance of <code>SSLClientError</code> without detail
     * message.
     */
    public SSLClientErrorException() {
    }

    /**
     * Constructs an instance of <code>SSLClientError</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public SSLClientErrorException(String msg) {
        super(msg);
    }
}
