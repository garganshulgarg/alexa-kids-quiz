package com.techhybris.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class FallbackIntentHandler  extends AbstractIntentHandler{


    @Override
	protected void handleInternal(HandlerInput input) {
		setSessionAttributes(input, "type", "help");
	}


}