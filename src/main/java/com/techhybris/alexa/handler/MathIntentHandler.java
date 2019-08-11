package com.techhybris.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import static com.amazon.ask.request.Predicates.intentName;

public class MathIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(MathIntentHandler.class);

	@Override
	protected void handleInternal(HandlerInput input) {
		setSessionAttributes(input, "quizName", "Math");
	}
	
	
	

	
	
	

}