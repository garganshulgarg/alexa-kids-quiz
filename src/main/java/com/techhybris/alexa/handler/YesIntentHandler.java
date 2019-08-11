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

public class YesIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(YesIntentHandler.class);
	String speechText;
	@Resource(name="helpIntentHandler")
	private HelpIntentHandler helpIntentHandler;
	@Resource(name="mathIntentHandler")
	private MathIntentHandler mathIntentHandler;
	@Resource(name="qAIntentHandler")
	private QAIntentHandler qAIntentHandler;
	
	@Override
	public Optional<Response> handle(HandlerInput input) {
		LOG.error("Inside YesIntentHandler");
		LOG.error("input.getAttributesManager().getSessionAttributes()"+input.getAttributesManager().getSessionAttributes().get("type"));
		
		if(input.getAttributesManager().getSessionAttributes().containsValue(null))
		{	//context not available
			speechText = "Sorry, I am not sure what you are saying Yes for.";
		}
		else if(input.getAttributesManager().getSessionAttributes().get("type").equals("math"))
		{
			LOG.error("INSIDE YesIntentHandler math");
			return mathIntentHandler.handle(input);
		}
		else if(input.getAttributesManager().getSessionAttributes().get("endQuiz") != null)
		{
			if(input.getAttributesManager().getSessionAttributes().get("endQuiz").equals("endQuiz"))
			{
				LOG.error("INSIDE YesIntentHandler endQuiz");
				setSessionAttributes(input, "endQuiz", "none");
				speechText = "Good Bye!";
			}
		}
		else if(input.getAttributesManager().getSessionAttributes().get("type").equals("resumeQuiz"))
		{
			LOG.error("INSIDE YesIntentHandler resumeQuiz");
			setSessionAttributes(input, "type", "answer");
			setSessionAttributes(input, "repeat", "repeat");
			return qAIntentHandler.handle(input);
		}
		else if(input.getAttributesManager().getSessionAttributes().get("resumeQuizReprompt") != null)
		{	
			if(input.getAttributesManager().getSessionAttributes().get("resumeQuizReprompt").equals("resumeQuizReprompt"))
			{
				LOG.error("INSIDE YesIntentHandler resumeQuizReprompt");
				setSessionAttributes(input, "resumeQuizReprompt", "none");
				setSessionAttributes(input, "repeat", "repeat");
				return qAIntentHandler.handle(input);
			}
		}
		else if(input.getAttributesManager().getSessionAttributes().get("type").equals("help"))
		{
			LOG.error("INSIDE YesIntentHandler help");
			return helpIntentHandler.handle(input);
		}
		else
		{	//context not recognized
			speechText = "Sorry, I do not understand how to process that.";
		}
		
		return input.getResponseBuilder().withSpeech(applyVoice2Speech(speechText)).build();
	
}

	@Override
	protected void handleInternal(HandlerInput input) {
		// TODO Auto-generated method stub
		
	}
	
}
