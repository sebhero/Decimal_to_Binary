package com.skola.controller;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLTextAreaElement;

import com.skola.tools.BinaryConverter;
import com.skola.tools.ExitDialogMessagebox;

/***
 * Handels events fired from the html page. This is instead of using Javascript.
 * Also the passage way to connect with the app from html Wich i belive and
 * think is one of the best way to programming. GWT is a nicer, probely safer
 * and better solution.
 * 
 * Im using bootstrapp 3.0 for creating the html UI.
 * 
 * {@link http://getbootstrap.com/}
 * 
 * @author seb
 * 
 */
public class HTMLController
{

	private final Element outputBinary;
	private final WebEngine engine;
	private String inputText;

	// bind the output textarea to a var
	public HTMLController(WebEngine engine)
	{
		this.engine = engine;
		outputBinary = (Element) engine.executeScript("document.getElementById('outputBinary')");

		// final HTMLAnchorElement newAnchor = (HTMLAnchorElement)
		// engine.getDocument().getElementById("newBinary");
	}

	/**
	 * Clears the the output field and removes validation errors
	 * 
	 */
	public void clear()
	{
		// clear old validation and output field
		engine.executeScript("$('#inputTextGroup').removeClass('has-error');");
		engine.executeScript("$('#inputTextGroup').find('label').addClass('hide');");

		// clear input and output fields
		outputBinary.setTextContent("");
	}

	/**
	 * Clears everything
	 * 
	 * @return
	 */
	public void newClear()
	{
		// clear the input field
		final HTMLTextAreaElement inputField = (HTMLTextAreaElement) engine
				.executeScript("document.getElementById('inputText')");

		inputField.setValue("");
		// clear everything else aswell
		clear();
	}

	/**
	 * handles menu on exit
	 */
	public void exit()
	{
		final ExitDialogMessagebox test = new ExitDialogMessagebox();
		final Boolean answear = test.showDialog();
		System.out.println("answear: " + answear);
		if (answear == true)
		{
			Platform.exit();
		}
	}

	/**
	 * Handles input from webpage. Has a direct connection to the button on the
	 * page.
	 */
	public void convert()
	{
		// clear errors and output field
		clear();

		// get the input text
		inputText = (String) engine.executeScript("document.getElementById('inputText').value");

		// alternative
		// final HTMLTextAreaElement inputField = (HTMLTextAreaElement) engine
		// .executeScript("document.getElementById('inputText')");

		// Do validation
		// display validation error
		if (inputText != null && !inputText.isEmpty())
		{
			// call converter
			final String binaryAnswear = BinaryConverter.converTextToBinary(inputText);
			// display convertion
			outputBinary.setTextContent(binaryAnswear);
		}
		else
		{

			// add error css classes
			engine.executeScript("$('#inputTextGroup').addClass('has-error');");
			engine.executeScript("$('#inputTextGroup').children().removeClass('hide');");
			outputBinary.setTextContent("");
		}

	}

	public void help()
	{
		System.out.println("calling help");
		// window.location.hash = '#food';
		// final JSObject window = (JSObject) engine.executeScript("window");

		// engine.load(engine.getLocation() + "#helpModal");
		// engine.executeScript("window.location = window.location + '#helpModal';window.location.reload();'");
		engine.executeScript("$('#helpModal').modal('show');");
	}

	public void openFileMenu()
	{
		engine.executeScript("$('#nav-main').collapse('toggle');");
	}
}
