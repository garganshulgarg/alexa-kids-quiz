package com.techhybris.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import static com.amazon.ask.request.Predicates.intentName;

public class QAIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(QAIntentHandler.class);
	

	@Override
	protected void handleInternal(HandlerInput input) {
		LOG.error("Inside QAIntentHandler");
		 setSessionAttributes(input, "resumeQuizReprompt", "resumeQuizReprompt");
		if(input.getAttributesManager().getSessionAttributes().containsValue("answer"))
		{	int score=(int) input.getAttributesManager().getSessionAttributes().get("score");
			int currentQuestionNumber=(int) input.getAttributesManager().getSessionAttributes().get("currentQuestionNumber");
			List<String> answerList=(List<String>) input.getAttributesManager().getSessionAttributes().get("answerList");
			List<String> questionList=(List<String>) input.getAttributesManager().getSessionAttributes().get("questionList");
			LOG.error("answerList::::"+answerList);
			String repeatValue;
			if(input.getAttributesManager().getSessionAttributes().get("repeat") == null)
			{	
				repeatValue="no";
			}
			else 
			{
				if(input.getAttributesManager().getSessionAttributes().get("repeat").equals("repeat"))
					repeatValue="yes";
				else
					repeatValue="no";
			}
			
			LOG.error("input.getAttributesManager().getSessionAttributes().get(\"repeat\")::"+input.getAttributesManager().getSessionAttributes().get("repeat"));
			LOG.error("repeatValue"+repeatValue);
			if(repeatValue.equals("no"))
			{ 	
				LOG.error("Inside repeat if");
				addModel(input, "repeatText", "notrepeat");
				String answerValue=null;
				String answer=answerList.get(currentQuestionNumber);
				LOG.error("answer"+answer);
				String answerReceived = null;
				Map<String, String> slots = getSlots(input);
				if(MapUtils.isNotEmpty(slots) && slots.containsKey("answer")) 
				{
					answerReceived= slots.get("answer");
				}
				LOG.error("answerReceived"+answerReceived);
				if(answer.equalsIgnoreCase(answerReceived))
				{
					LOG.error("Inside correct");
					score++;
					answerValue="correctAnswer";
					addModel(input, "score", score);
				}
				else
				{
					LOG.error("Inside incorrect");
					answerValue="incorrectAnswer";
					addModel(input, "answer", answer);
					addModel(input, "score", score);
				}
				setSessionAttributes(input, "score", score);
				addModel(input, "answerValue", answerValue);
				if(currentQuestionNumber<4)
				{
				  currentQuestionNumber++;
				  addModel(input, "speachName", "nextQuestion");
				  addModel(input, "question", questionList.get(currentQuestionNumber));
				  setSessionAttributes(input, "type", "answer");
				  setSessionAttributes(input, "currentQuestionNumber", currentQuestionNumber);
				}
				else
				{
					addModel(input, "speachName", "end");
					addModel(input, "score", score);
					setSessionAttributes(input, "endQuiz", "endQuiz");
				}
			}
			else
			{
				  LOG.error("Inside repeat else");
				  addModel(input, "question", questionList.get(currentQuestionNumber));
				  addModel(input, "repeatText", "repeat");
				  setSessionAttributes(input, "type", "answer");
				  setSessionAttributes(input, "currentQuestionNumber", currentQuestionNumber);
				  setSessionAttributes(input, "score", score);
			}
			setSessionAttributes(input, "repeat", "none");
			
		}
		else
		{
			addModel(input, "repeatText", "notrepeat");
			addModel(input, "speachName", "error");
		}
		
	}
}