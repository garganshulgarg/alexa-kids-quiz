package com.techhybris.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import static com.amazon.ask.request.Predicates.intentName;

public class RepeatIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(RepeatIntentHandler.class);
	
	@Resource(name="yesIntentHandler")
	private YesIntentHandler yesIntentHandler; 

	@Override
	protected void handleInternal(HandlerInput input) {
		LOG.error("Inside RepeatIntentHandler");
		String speechText=null;
		if(input.getAttributesManager().getSessionAttributes().get("type").equals("answer"))
			repeatQuiz(input);
		else
		{
			speechText="displayMsg";
			addModel(input, "speechText", speechText);
		}
	}
	
	public Optional<Response> repeatQuiz(HandlerInput input) {
			LOG.error("Inside RepeatIntentHandler repeatQuiz");
			setSessionAttributes(input, "type", "resumeQuiz");
			return yesIntentHandler.handle(input);
	}

	
}