package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;

public class Main {

	public static void main(String[] args){
		List<String> articles = new ArrayList<>();
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5977659.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5987019.ece");
		articles.add("http://mediawatch.dk/Medienyt/Ugeblade_magasiner/article5986964.ece");
		articles.add("http://mediawatch.dk/Medienyt/Web/article5974581.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5970002.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5964256.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5967381.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966624.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966172.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5966147.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966142.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963904.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5961909.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961933.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961784.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5961642.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959329.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5958428.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957842.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957270.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5957120.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5949298.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5950736.ece");
		articles.add("http://mediawatch.dk/Medienyt/Aviser/article5935550.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5977659.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5987019.ece");
		articles.add("http://mediawatch.dk/Medienyt/Ugeblade_magasiner/article5986964.ece");
		articles.add("http://mediawatch.dk/Medienyt/Web/article5974581.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5970002.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5964256.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5967381.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966624.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966172.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5966147.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966142.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963904.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5961909.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961933.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961784.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5961642.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959329.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5958428.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957842.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957270.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5957120.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5949298.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5950736.ece");
		articles.add("http://mediawatch.dk/Medienyt/Aviser/article5935550.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5977659.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5987019.ece");
		articles.add("http://mediawatch.dk/Medienyt/Ugeblade_magasiner/article5986964.ece");
		articles.add("http://mediawatch.dk/Medienyt/Web/article5974581.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5970002.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5964256.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5967381.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966624.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966172.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5966147.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966142.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963904.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5961909.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961933.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961784.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5961642.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959329.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5958428.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957842.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957270.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5957120.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5949298.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5950736.ece");
		articles.add("http://mediawatch.dk/Medienyt/Aviser/article5935550.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5977659.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5987019.ece");
		articles.add("http://mediawatch.dk/Medienyt/Ugeblade_magasiner/article5986964.ece");
		articles.add("http://mediawatch.dk/Medienyt/Web/article5974581.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5970002.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5964256.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5967381.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966624.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966172.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5966147.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5966142.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963904.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5963196.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5961909.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961933.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5961784.ece");
		articles.add("http://mediawatch.dk/Medienyt/article5961642.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959378.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5959329.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5958428.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957842.ece");
		articles.add("http://mediawatch.dk/Medienyt/TV/article5957270.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5957120.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5949298.ece");
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5950736.ece");
		articles.add("http://mediawatch.dk/Medienyt/Aviser/article5935550.ece");

		//AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken("371367926286893", "6db261edfe58c9f6ac2d307740434245");
		//accessToken.getAccessToken()

		//Default without accessToken, hereby granted read permission as normal user.
		FacebookClient facebookClient = new DefaultFacebookClient();

		long start = System.currentTimeMillis();
		Map<String, String> queries = new HashMap<String, String>();
		for(int i=0; i<120; i++){
			queries.put("information", "select time, text, fromid from comment where object_id in (select comments_fbid from link_stat where url='" + articles.get(i) + "')");
			queries.put("person", "select name from user where uid in (SELECT fromid FROM #information)");
			MultiqueryResults multiqueryResults = facebookClient.executeFqlMultiquery(queries, MultiqueryResults.class);

			//Here we should make something to save the information in. Right now only print
			System.out.println("Users: " + multiqueryResults.information);
			System.out.println("Name: " + multiqueryResults.person);
		}
		System.out.println((System.currentTimeMillis())-start);

	}

	public static class MultiqueryResults {
		@Facebook
		List<FqlTest> information;

		@Facebook
		List<FqlPerson> person;
	}

	public static class FqlPerson {
		@Facebook("name")
		String userId;

		@Override
		public String toString() {
			return userId;
		}  
	}

	public static class FqlTest{
		@Facebook
		String time;

		@Facebook
		String text;

		@Facebook
		String fromid;

		@Override
		public String toString(){
			return time + " " + text + " " + fromid;
		}
	}
}