/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igc_utils.exceptions;

/**
 *
 * @author jfrese
 */
public class NoIgcFileException extends Exception {

    String pathName="";
    
    public NoIgcFileException(String pName) {
        super();
        pathName = pName;
    }

    public NoIgcFileException(String message,String pName) {
        super(message);
        pathName = pName;
    }

    public NoIgcFileException(String message, Throwable cause,String pName) {
        super(message, cause);
        pathName = pName;
    }

    protected NoIgcFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,String pName) {
        super(message, cause, enableSuppression, writableStackTrace);
        pathName = pName;
    }
    
    public NoIgcFileException(Throwable cause,String pName) {
        super(cause);
        pathName = pName;
    }
    
    public String getPathName(){
        return pathName;
    }
}
