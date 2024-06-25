package com.zyp.plugin.core.activity.hook;

/**
 * @author Lody
 */
public class ReflectException extends RuntimeException {

	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflectException(Throwable cause) {
		super(cause);
	}
}
