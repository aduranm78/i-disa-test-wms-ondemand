package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;

public class WmsParams {
	
	public static String getDateRange (int secondsAgo) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date startDate;
		Date endDate;
		JSONObject dates = new JSONObject();
		TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");

		Date now = new Date(System.currentTimeMillis());
      	Calendar c = Calendar.getInstance();
      	c.setTimeZone(tz);
      	formatter.setTimeZone(tz);
        c.setTime(now);
        c.add(Calendar.SECOND, secondsAgo * -1);
        startDate = c.getTime();
        endDate = now;
      	
      	dates.put("start", formatter.format(startDate));
        dates.put("end", formatter.format(endDate));

        return dates.toString();
      }
}
