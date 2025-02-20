package com.productmanagement.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(final Exception exception) {
		if (exception instanceof ProductNotFoundException) {
			return Response
						.status(Response.Status.NOT_FOUND)
						.entity(new ErrorResponseBody(exception.getMessage()))
						.build();
		}
		return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorResponseBody(exception.getMessage()))
					.build();
	}

	public static final class ErrorResponseBody {

		private final String message;

		public ErrorResponseBody(final String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
