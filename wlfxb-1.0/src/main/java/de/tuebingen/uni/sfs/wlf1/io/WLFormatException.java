package de.tuebingen.uni.sfs.wlf1.io;

/**
 * An Exception to be thrown by TextCorpusFormat
 *
 * @author akislev
 */
public class WLFormatException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1650046637471293795L;

    /**
     * Create from a string message
     *
     * @param msg a string message
     */
    public WLFormatException(String msg) {
        super(msg);
    }

    /**
     * create from an exception
     *
     * @param ex an exception
     */
    public WLFormatException(Throwable ex) {
        super.initCause(ex);
    }

    /**
     * create from a message and an exception
     *
     * @param msg a message
     * @param ex an exception
     */
    public WLFormatException(String msg, Throwable ex) {
        super(msg);
        super.initCause(ex);
    }
}
