package usp.ime.line.ivprog;


public class Tracking {

	private static Tracking instance;
	private String baseURL;
	
	public static Tracking getInstance(){
		if(instance==null){
			instance = new Tracking();
		}
		return instance;
	}
	public void setBase(String baseURL){
		this.baseURL = baseURL+"&track=1";
	}
	public void track(String trackingData){
		 HttpUtil httpUtil = new HttpUtil();
        // variaveis post
        httpUtil.addPostVariable("trackingData", trackingData);
        try {
            httpUtil.makePostRequest(baseURL);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
