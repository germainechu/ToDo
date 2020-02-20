package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ui.PomoTodoApp.setScene;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoViewActionsPopUpfxmlFile = new File(todoActionsPopUpFXML);
    private File todoViewOptionsPopUpfxmlFile = new File(todoOptionsPopUpFXML);

    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup todobarActionsPopUp;
    private JFXPopup todobarOptionsPopUp;

    private Task task;
    //private File

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodoBarActionsPopUp();
        loadToolbarPopUpActionListener();
        loadViewOptionsPopUp();
        loadViewOptionsPopUpActionListener();
    }

    // EFFECTS: load options pop up (setting, exit)
    private void loadTodoBarActionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoViewActionsPopUpfxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodobarPopUpController());
            todobarActionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: load view selector pop up (list view, priority view, status view)
    private void loadViewOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoViewOptionsPopUpfxmlFile.toURI().toURL());
            fxmlLoader.setController(new ViewOptionsPopUpController());
            todobarOptionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // EFFECTS: show view selector pop up when its icon is clicked
    // offset 12 to get this bread.
    private void loadViewOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todobarOptionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }

    // EFFECTS: show options pop up when its icon is clicked
    private void loadToolbarPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                todobarActionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // Inner class: view selector pop up controller
    class ViewOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        // In the case 2 we are using the helper delete function to delete the entire task.
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("EditTask", "Editing this task");
                    editAppTask();
                    break;
                case 1:
                    Logger.log("DeleteTask", "Deleting this task");
                    deleteThisTask();
                    break;
                default:
                    Logger.log("TodobarActionsPopUpController", "No action is implemented for the selected option");
            }
            todobarOptionsPopUp.hide();
        }

        @FXML
        // Here we are deleting the task from the Pomotodoapp.
        public void deleteThisTask() {
            try {
                PomoTodoApp.getTasks().remove(task);
                JsonFileIO.write(PomoTodoApp.getTasks());
                JsonFileIO.read();
            } catch (RuntimeException e) {
                Logger.log("DeleteTaskController", "Failed to delete task!");
            } finally {
                returnToListView();
            }
        }

        @FXML
        private void editAppTask() {
            setScene(new EditTask(task));
        }

        private void returnToListView() {
            Logger.log("AddTaskController", "Return to the list view UI.");
            setScene(new ListView(PomoTodoApp.getTasks()));
        }
    }

    // Inner class: option pop up controller
    class TodobarPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        // Adding the different cases using the logger method
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                case 1: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                case 2: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                case 3: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                case 4: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                case 5: Logger.log("TodoActionsPopUpController", "Setting is not supported in this version");
                    break;
                default: Logger.log("TodoActionsPopUpController", "No action is implemented for the selected option");
            }
            todobarActionsPopUp.hide();
        }


    }

}



