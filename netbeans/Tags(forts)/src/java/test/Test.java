package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
	// boolean
    private boolean selectedBoolean;

    public boolean isSelectedBoolean() {
        return selectedBoolean;
    }

    public void setSelectedBoolean(boolean selectedBoolean) {
        this.selectedBoolean = selectedBoolean;
    }

    // int
    private int[] ints;
    private int selectedInt;
    private int[] selectedInts;

    public int[] getInts() {
        if (ints == null) {
            ints = new int[]{3, 4, 5, 6, 7, 8};
        }
        return ints;
    }

    public int getSelectedInt() {
        return selectedInt;
    }

    public void setSelectedInt(int selectedInt) {
        this.selectedInt = selectedInt;
    }

    public int[] getSelectedInts() {
        return selectedInts;
    }

    public void setSelectedInts(int[] selectedInts) {
        this.selectedInts = selectedInts;
    }
    
    // String
    private List<String> strings;
    private String selectedString;
    private String[] selectedStrings;

    public List<String> getStrings() {
        if (strings == null) {
            strings = new ArrayList<String>();
            strings.add("One");
            strings.add("Two");
            strings.add("Three");
            strings.add("Four");
        }
        return strings;
    }

    public String getSelectedString() {
        return selectedString;
    }

    public void setSelectedString(String selectedString) {
        this.selectedString = selectedString;
    }

    public String[] getSelectedStrings() {
        return selectedStrings;
    }

    public void setSelectedStrings(String[] selectedStrings) {
        this.selectedStrings = selectedStrings;
    }
    
    // Person
    private List<Person> persons;
    private int selectedPersonId;
    private int[] selectedPersonIds;

 
    public List<Person> getPersons() {
        if (persons == null) {
            persons = new ArrayList<Person>();
            persons.add(new Person(1, "Ida"));
            persons.add(new Person(2, "Ove"));
            persons.add(new Person(3, "Åge"));
        }
        return persons;
    }
    
    public int getSelectedPersonId() {
		return selectedPersonId;
	}

	public void setSelectedPersonId(int selectedPersonId) {
		this.selectedPersonId = selectedPersonId;
	}

	public int[] getSelectedPersonIds() {
		return selectedPersonIds;
	}

	public void setSelectedPersonIds(int[] selectedPersonIds) {
		this.selectedPersonIds = selectedPersonIds;
	}

	// print ...
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String printSelected() {
        text = "" + selectedBoolean;
        return null;
    }

    public String printSelectOne() {
        text = selectedInt + "\n";
        text += selectedString + "\n";
        Person find = new Person(selectedPersonId, null);
        text += persons.get(persons.indexOf(find));
        return null;
    }

    public String printSelectMany() {
        text = "";
        for (int i : selectedInts) {
            text += i + ", ";
        }

        text += "\n";
        for (String s : selectedStrings) {
            text += s + ", ";
        }

        text += "\n";
        for (int id : selectedPersonIds) {
            Person find = new Person(id, null);
            text += persons.get(persons.indexOf(find)) + ", ";
        }
        return null;
    }
}
