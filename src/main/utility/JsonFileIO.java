package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static persistence.Jsonifier.taskListToJson;


// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");


    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser parser = new TaskParser();
        List<Task> listoftodotasks = new ArrayList<>();
        try {
            String contents = new String(Files.readAllBytes(Paths.get(jsonDataFile.getPath())));
            listoftodotasks = parser.parse(contents);
        } catch (Exception jsonexception) {
            System.out.println(jsonexception.toString());
        }
        return listoftodotasks;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            JSONArray jsontodo = Jsonifier.taskListToJson(tasks);
            String value = jsontodo.toString();
            String str = value;

            Path path = Paths.get(jsonDataFile.getPath());
            byte[] strToBytes = str.getBytes();
            Files.write(path, strToBytes);

        } catch (IOException notgoodexception) {
            System.out.println(notgoodexception.toString());
        }

    }
}
