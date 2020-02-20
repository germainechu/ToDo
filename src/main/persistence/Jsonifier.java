package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagjson = new JSONObject();
        tagjson.put("name", tag.getName());

        return tagjson;

    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityjson = new JSONObject();
        priorityjson.put("important", priority.isImportant());
        priorityjson.put("urgent", priority.isUrgent());
        return priorityjson;

    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject duedatejson = new JSONObject();
        if (dueDate == null) {
            return null;
        }
        Date duedate = dueDate.getDate();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(duedate);
        duedatejson.put("year", calendario.get(Calendar.YEAR));
        duedatejson.put("month", calendario.get(Calendar.MONTH));
        duedatejson.put("day", calendario.get(Calendar.DAY_OF_MONTH));
        duedatejson.put("hour", calendario.get(Calendar.HOUR_OF_DAY));
        duedatejson.put("minute", calendario.get(Calendar.MINUTE));

        return duedatejson;

    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskjson = new JSONObject();
        taskjson.put("description", task.getDescription());
        taskjson.put("tags", tagstoJsonformat(task));
        taskjson.put("due-date", dueDateToJson(task.getDueDate()));
        taskjson.put("priority", priorityToJson(task.getPriority()));
        taskjson.put("status", task.getStatus());
        return taskjson;
    }

    public static JSONArray tagstoJsonformat(Task task) {
        JSONArray tagsarray = new JSONArray();

        for (Tag t : task.getTags()) {
            tagsarray.put(tagToJson(t));

        }
        return tagsarray;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksarray = new JSONArray();

        for (Task task : tasks) {
            tasksarray.put(taskToJson(task));
        }

        return tasksarray;
    }
}
