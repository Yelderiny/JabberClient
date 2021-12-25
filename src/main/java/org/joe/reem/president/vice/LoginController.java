package org.joe.reem.president.vice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController
{
    @FXML private Label displayMsg;
    @FXML private TextField username;
    private final StreamManager stream = new StreamManager();


    @FXML protected void onSignInButtonClick(final ActionEvent event) throws IOException, ClassNotFoundException { buttonHelper("signin", event); }
    @FXML protected void onRegisterButtonClick(final ActionEvent event) throws IOException, ClassNotFoundException { buttonHelper("register", event); }

    /**
     * Sends the desired action to the server and signs the user either in or up if there are no issues
     * @param message an indicator of whether this is a sign-up or sign-in action
     * @param event the button click
     */
    private void buttonHelper(final String message, final ActionEvent event) throws IOException, ClassNotFoundException
    {
        //no text was entered in text field
        if (Objects.equals(username.getText(), ""))
        {
            displayMsg.setText("Enter a username, bro");
            displayMsg.setVisible(true); //show the text
        }
        else
        {
            JabberMessage reply = stream.exchange(message + " " + username.getText()); //send the message and get the reply from the server

            if (reply.getMessage().equals("signedin")) switchScene(event); //got the reply, switch the scene to the main page
            else
            {
                displayMsg.setText("Action failed. This must be embarrassing for you bro");
                displayMsg.setVisible(true);
            }
        }
    }

    /**
     * Hides the current scene and switches to the new scene
     * @param event successful sign-in or sign-up
     * @throws IOException
     */
    private void switchScene(final ActionEvent event) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide(); //hide the current scene
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("MainPage.fxml")); //get the fxml file info
        Scene scene = new Scene(fxmlLoader.load()); //feed the fxml file info into the scene

        Stage stage = new Stage(); //instantiate new stage object
        stage.setTitle("Welcome " + username.getText()); //setting the toolbar title
        stage.setResizable(false); //not allowing stage to be resized
        stage.setScene(scene); //add the scene to the stage

        stage.show(); //show the stage
    }
}