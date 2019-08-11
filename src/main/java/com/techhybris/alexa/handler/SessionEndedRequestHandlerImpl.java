package com.techhybris.alexa.handler;
import  com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

public class SessionEndedRequestHandlerImpl implements SessionEndedRequestHandler {

	@Override
	public boolean canHandle(HandlerInput input, SessionEndedRequest sessionEndedRequest) {
		return true;
	}

	@Override
	public Optional<Response> handle(HandlerInput input, SessionEndedRequest sessionEndedRequest) {
		final String speechText = "Good Bye!";
		return input.getResponseBuilder().withSpeech(speechText).build();
	}

	

	
}
