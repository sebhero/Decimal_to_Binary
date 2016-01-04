package com.skola.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import com.skola.controller.HTMLController;

public class BinaryConverterHTMLScene
{

	HTMLController myHtmlController;

	public void start(final Stage primaryStage)
	{
		try
		{

			// skapa en webview f�r att visa en websida
			final WebView webView = new WebView();
			final WebEngine engine = webView.getEngine();

			webView.getEngine().load(getClass().getResource("/index.html").toString());

			// Koppla ihop Java k�den med websidan
			engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
			{

				// v�nta tills sidan �r laddad.
				@Override
				public void changed(ObservableValue observable, State oldValue, State newValue)
				{
					if (newValue == State.SUCCEEDED)
					{
						// call stuff on the webpage.
						// get the window js Object (root)
						final JSObject window = (JSObject) engine.executeScript("window");

						// add a java code to JS connection
						// Nu kan jag p� min websida skriva java.convert()
						// som �r en funktion i klassen JSCommunicator.
						myHtmlController = new HTMLController(engine);
						window.setMember("java", myHtmlController);
					}

					// display error msg if failed to load the webpage. t.e.x s�kv�gen �r
					// fel.
					if (newValue == State.FAILED)
					{
						System.err.println("Failed to load webpage");
					}
				}
			});

			// skapa en scen f�r att visa min websida
			final Scene scene = new Scene(webView, 400, 400);

			// adds accelerators
			setAcceleratorsToScene(scene);

			// l�gger till scenen till stage och visar stage.
			primaryStage.setScene(scene);
			primaryStage.show();

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add accellerators to the html scene
	 * 
	 * @param scene
	 */
	private void setAcceleratorsToScene(final Scene scene)
	{
		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN), new Runnable()
		{

			@Override
			public void run()
			{
				myHtmlController.openFileMenu();
			}
		});

		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.ALT_DOWN), new Runnable()
		{

			@Override
			public void run()
			{
				myHtmlController.newClear();
			}
		});

		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN), new Runnable()
		{

			@Override
			public void run()
			{
				myHtmlController.exit();
			}
		});

		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN), new Runnable()
		{

			@Override
			public void run()
			{
				myHtmlController.convert();
			}
		});

		scene.getAccelerators().put(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN), new Runnable()
		{

			@Override
			public void run()
			{
				myHtmlController.help();
			}
		});
	}
}
