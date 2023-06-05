package com.santander.internet.banking.exception;

public class NaoEncontradoException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public NaoEncontradoException(String msg){
		this.message = msg;
	}
	
	  public String getMessage(){
	    return this.message;
	  }

}
