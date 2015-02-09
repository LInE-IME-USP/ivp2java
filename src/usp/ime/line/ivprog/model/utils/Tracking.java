package usp.ime.line.ivprog.model.utils;

public class Tracking {
	private static Tracking instance;
	private String baseURL;
	private boolean isApplet = false;

	public static Tracking getInstance() {
		if (instance == null) {
			instance = new Tracking();
		}
		return instance;
	}

	public void setBase(String baseURL) {
		this.baseURL = baseURL + "&track=1";
		isApplet = true;
	}

	public void track(String trackingData) {
		if (isApplet) {
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

	public boolean isApplet() {
		return isApplet;
	}

	public void setApplet(boolean isApplet) {
		this.isApplet = isApplet;
	}
}
