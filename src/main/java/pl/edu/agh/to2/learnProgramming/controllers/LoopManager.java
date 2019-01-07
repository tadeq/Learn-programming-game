package pl.edu.agh.to2.learnProgramming.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import pl.edu.agh.to2.learnProgramming.command.Command;
import pl.edu.agh.to2.learnProgramming.command.ComplexCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LoopManager {
    private int loopsOpened;

    private List<Integer> loopsRepeatList;

    public LoopManager() {
        loopsOpened = 0;
        loopsRepeatList = new LinkedList<>();
    }

    public void decLoopsOpened() {
        loopsOpened--;
    }

    public void incLoopsOpened() {
        loopsOpened++;
    }

    public void resetLoopsOpened() {
        loopsOpened = 0;
    }

    public void removeLoop(int index) {
        this.loopsRepeatList.remove(index);
    }

    public List<Integer> getLoopsRepeatList() {
        return this.loopsRepeatList;
    }

    public boolean openLoop() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Loop");
        dialog.setHeaderText("Loop repeats:");
        dialog.setContentText("Value:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int number;
            if (result.get().equals(""))
                number = 1;
            else
                number = Integer.parseInt(result.get());
            if (result.get().equals("") || number <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Value not allowed. Repeats has been set to 1.");
                alert.showAndWait();
            }
            loopsRepeatList.add(number);
            loopsOpened++;
            return true;
        }
        return false;
    }

    public boolean closeLoop() {
        if (loopsOpened == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("You have to start the loop before ending it");
            alert.show();
            return false;
        } else {
            loopsOpened--;
            return true;
        }
    }

    public boolean loopsGood(List<Command> commands) {
        Alert alert;
        if (loopsOpened != 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Number of startLoop has to be equal to number of endLoop. Check your program.");
            alert.show();
            return false;
        } else {
            for (Command command : commands) {
                if (command.isLoop())
                    loopsOpened = ((ComplexCommand) command).changeLoopsOpened(loopsOpened);
                if (loopsOpened < 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Can't end loop before starting it. Check your program.");
                    alert.show();
                    return false;
                }
            }
        }
        return true;
    }
}
