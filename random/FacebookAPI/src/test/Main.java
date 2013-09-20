package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class Main {

	public static void main(String[] args){
		List<String> articles = new ArrayList<>();
		articles.add("http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece");

		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken("371367926286893", "6db261edfe58c9f6ac2d307740434245");
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken());

		long start = System.currentTimeMillis();
		Map<String, String> queries = new HashMap<String, String>();
		queries.put("users", "select time, text, fromid from comment where object_id in (select comments_fbid from link_stat where url='http://mediawatch.dk/secure/Medienyt/TV/article5974698.ece')");
		queries.put("comments", "select name from user where uid in (SELECT fromid FROM #users)");
		for(int i=0; i<30; i++){


			//
			//		Map<String, String> queries2 = new HashMap<String, String>();
			//		queries2.put("all_posts", "select created_time, post_id, actor_id, message, description, comments from stream where source_id=279947942083512 ORDER BY created_time");
			//		queries2.put("comments", "SELECT fromid, username, text, time, post_id FROM comment WHERE post_id in (SELECT post_id FROM #all_posts) ORDER BY post_id");


			MultiqueryResults multiqueryResults = facebookClient.executeFqlMultiquery(queries, MultiqueryResults.class);
			System.out.println("Users: " + multiqueryResults.users);
			System.out.println("People who liked: " + multiqueryResults.comments);
		}
		System.out.println((System.currentTimeMillis())-start);

	}

	public static class MultiqueryResults {
		@Facebook
		List<FqlTest> users;

		@Facebook
		List<FqlLiker> comments;
	}

	public static class FqlLiker {
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