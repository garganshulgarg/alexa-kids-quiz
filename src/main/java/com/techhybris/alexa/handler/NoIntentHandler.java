package com.techhybris.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(NoIntentHandler.class);
	

	@Override
	protected void handleInternal(HandlerInput input) {
		String speachText=null;
		if(input.getAttributesManager().getSessionAttributes().get("endQuiz") != null)
		{
			if(input.getAttributesManager().getSessionAttributes().get("endQuiz").equals("endQuiz"))
			{
				speachText="startAgain";
				setSessionAttributes(input, "endQuiz", "none");
				setSessionAttributes(input, "type", "help");
			}
			else
			{
				speachText="exit";
			}
		}
		else
		{
			speachText="exit";
		}
		addModel(input, "speachText", speachText);
	}


	
	
	

}