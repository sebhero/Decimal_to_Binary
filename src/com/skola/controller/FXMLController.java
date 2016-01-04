package com.skola.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import com.skola.tools.BinaryConverter;
import com.skola.tools.ExitDialogMessagebox;

public class FXMLController
{

	@FXML
	TextArea inputText;

	@FXML
	TextArea outputBinary;

	public FXMLController()
	{
	}

	/**
	 * Clears the fields
	 */
	@FXML
	private void clearFields()
	{
		System.out.println("clearing areas");
		inputText.clear();
		outputBinary.clear();
	}

	/**
	 * Do the convert to binary
	 */
	@FXML
	private void convertToBinary()
	{
		System.out.println("converting");

		if (inputText != null && !inputText.getText().isEmpty())
		{
			// call converter
			final String binaryAnswear = BinaryConverter.converTextToBinary(inputText.getText());
			// display convertion
			outputBinary.setText(binaryAnswear);
		}
		else
		{
			System.out.println("validation error");
			outputBinary.setText("");
		}

	}

	/**
	 * exit from menu
	 */
	@FXML
	private void Quit()
	{

		final ExitDialogMessagebox test = new ExitDialogMessagebox();
		final Boolean answear = test.showDialog();
		System.out.println("answear: " + answear);
		if (answear == true)
		{
			Platform.exit();
		}
	}
}
