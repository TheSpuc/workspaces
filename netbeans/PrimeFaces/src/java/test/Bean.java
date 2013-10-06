package test;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

@Named
@SessionScoped
public class Bean implements Serializable {
	private String text;

	private PieChartModel model;

	public Bean() {
		model = new PieChartModel();
		model.set("Emne 1", 540);
		model.set("Emne 2", 325);
		model.set("Emne 3", 702);
		model.set("Emne 4", 421);
	}

	public String getPattern() {
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance(
				DateFormat.LONG, Locale.getDefault());

		return sdf.toPattern();
	}

	public PieChartModel getModel() {
		return model;
	}

	public List<String> complete(String query) {
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
			results.add(query + i);
		return results;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}