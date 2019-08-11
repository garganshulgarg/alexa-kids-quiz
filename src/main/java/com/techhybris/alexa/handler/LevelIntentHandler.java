package com.techhybris.alexa.handler;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

public class LevelIntentHandler extends AbstractIntentHandler {

	private Logger LOG = LoggerFactory.getLogger(LevelIntentHandler.class);
	

	@Override
	protected void handleInternal(HandlerInput input) {
		LOG.error("Inside LevelIntentHandler:");
		setSessionAttributes(input, "resumeQuizReprompt", "resumeQuizReprompt");
		LOG.error((String) input.getAttributesManager().getSessionAttributes().get("quizName"));
		if(input.getAttributesManager().getSessionAttributes().get("quizName") != null)
		{
			String quizName= (String) input.getAttributesManager().getSessionAttributes().get("quizName");
			setSessionAttributes(input, "quizName", "none");
			LOG.error("quizName::"+quizName);
			if(quizName.equals("Math") || quizName.equals("GK"))
			{
					String levelNumber = null;
					ArrayList<String> questionList = new ArrayList<String>();
					ArrayList<String> answerList = new ArrayList<String>();
					Map<String, String> slots = getSlots(input);
					if(MapUtils.isNotEmpty(slots) && slots.containsKey("levelNumber")) 
					{
						levelNumber= slots.get("levelNumber");
					}
					LOG.error("levelnumber::"+levelNumber);
					String fileName="/view/speech/"+quizName+levelNumber+"qA.properties";
					LOG.error("fileName::"+fileName);
					try {
						FileReader reader=new FileReader(getResourcePath() + fileName);  
						Properties p=new Properties();  
					    p.load(reader); 
					    ArrayList<String> list = new ArrayList<String>();
					    for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements(); ) {
					        String name = (String)e.nextElement();
					        String value = p.getProperty(name);
					        list.add(value); 
						}
					    Collections.shuffle(list);
					    List<String> selectedList=list.subList(0, 5);
					    for(int i=0;i<5;i++)
					    {
					    	String questionAnswer=selectedList.get(i);
					    	questionList.add(questionAnswer.split(";")[0]);
					    answerList.add(questionAnswer.split(";")[1]);
					    }
					}
					catch(Exception e)
					{
						   LOG.error("Exception:"+ e);
					}
				  setSessionAttributes(input, "questionList", questionList);
				  setSessionAttributes(input, "answerList", answerList);
				  setSessionAttributes(input, "type", "answer");
				  setSessionAttributes(input, "currentQuestionNumber", 0);
				  setSessionAttributes(input, "score", 0);
				  addModel(input, "levelNumber", levelNumber);
				  addModel(input, "quizName", quizName);
				  addModel(input, "question", questionList.get(0));
				  addModel(input, "speachText", "levelMsg");
				  
			  }
			  else
			  {
				  addModel(input, "speachText", "error");
					
			  }
	  }
	  else
	  {
		  addModel(input, "speachText", "error");
			
	  }
	}
}
