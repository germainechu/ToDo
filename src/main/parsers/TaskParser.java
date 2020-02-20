package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        List listoftasks = new ArrayList<Task>();
        JSONArray taskarray = new JSONArray(input);

        for (Object objecto : taskarray) {
            try {
                JSONObject jsontask = (JSONObject) objecto;
                Task task = new Task("noname");
                task.setDescription(jsontask.getString("description"));
                jsontagstolistoftags(jsontask.getJSONArray("tags"));
                duedateifelse(jsontask, task);
                task.setPriority(jsontopriority(jsontask.getJSONObject("priority")));
                task.setStatus(jsontostatus(jsontask));
                listoftasks.add(task);
            } catch (JSONException je) {
                continue;
            }
        }
        return listoftasks;
    }


    public List<Tag> jsontagstolistoftags(JSONArray jsontags) {
        ArrayList<Tag> listoftags = new ArrayList<Tag>();
        for (Object taggy : jsontags) {
            JSONObject tag = (JSONObject) taggy;
            String nameoftag = tag.getString("name");
            listoftags.add(new Tag(nameoftag));
        }
        return listoftags;

    }

    private void duedateifelse(JSONObject jsontask, Task task) {
        if (jsontask.isNull("due-date")) {
            task.setDueDate(null);
        } else {
            task.setDueDate(jsondatetoduedate(jsontask.getJSONObject("due-date")));
        }
    }

    public DueDate jsondatetoduedate(JSONObject duedate) {
        Calendar calendario = Calendar.getInstance();
//        System.out.println(duedate.toString(1));
        calendario.set(duedate.getInt("year"),
                duedate.getInt("month"),
                duedate.getInt("day"),
                duedate.getInt("hour"),
                duedate.getInt("minute"));
        Date date = calendario.getTime();
        DueDate dobydate = new DueDate(date);

        return dobydate;
    }

    public Priority jsontopriority(JSONObject jp) {
        Priority priority = new Priority(1);
        priority.setUrgent(jp.getBoolean("urgent"));
        priority.setImportant(jp.getBoolean("important"));
        return priority;
    }

    public Status jsontostatus(JSONObject jp) {
        if (jp.getString("status").equals("TODO")) {
            return Status.TODO;
        } else if (jp.getString("status").equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else if (jp.getString("status").equals("DONE")) {
            return Status.DONE;
        } else if (jp.getString("status").equals("UP_NEXT")) {
            return Status.UP_NEXT;
        }

        return Status.TODO;

    }
}
