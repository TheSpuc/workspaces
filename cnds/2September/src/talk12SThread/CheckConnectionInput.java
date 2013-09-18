package talk12SThread;

public class CheckConnectionInput {
	
	/**
	 * Method for checking whether or not 
	 * the input is giving a false or true connection
	 * @param input
	 * @return
	 */
	public static synchronized boolean ongoingConnection(String input){
		boolean result = false;
		if(input.startsWith(" ")){
			result = true;
		}else if(input.startsWith("#")){
			String test = input.substring(1, 4);
			if(test.equals("200")){
				result = true;
			}else if(test.equals("403") || test.equals("410")){
				result = false;
			}
		}
		return result;
	}
}
