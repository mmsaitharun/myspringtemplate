package oneapp.demo.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import oneapp.demo.exception.InvalidInputFault;

/**
 * Contains utility functions to be used by Services
 * 
 * @version R1
 */
public class ServicesUtil {

	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> o) {
		if (o == null || o.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuffer sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(StringBuilder sb) {
		if (sb == null || sb.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static String getCSV(Object... objs) {
		if (!isEmpty(objs)) {
			if (objs[0] instanceof Collection<?>) {
				return getCSVArr(((Collection<?>) objs[0]).toArray());
			} else {
				return getCSVArr(objs);
			}

		} else {
			return "";
		}
	}

	private static String getCSVArr(Object[] objs) {
		if (!isEmpty(objs)) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : objs) {
				sb.append(',');
				if (obj instanceof Field) {
					sb.append(extractFieldName((Field) obj));
				} else {
					sb.append(extractStr(obj));
				}
			}
			sb.deleteCharAt(0);
			return sb.toString();
		} else {
			return "";
		}
	}

	public static String buildNoRecordMessage(String queryName, Object... parameters) {
		StringBuffer sb = new StringBuffer("No Record found for query: ");
		sb.append(queryName);
		if (!isEmpty(parameters)) {
			sb.append(" for params:");
			sb.append(getCSV(parameters));
		}
		return sb.toString();
	}

	public static String extractStr(Object o) {
		return o == null ? "" : o.toString();
	}

	public static String extractFieldName(Field o) {
		return o == null ? "" : o.getName();
	}

	public static String appendLeadingCharsToInt(int input, char c, int finalSize) throws InvalidInputFault {
		if (finalSize > 0 && !isEmpty(input)) {
			return String.format("%"+c+finalSize+"d", input);
		}
		return String.valueOf(input);
	}

	public static String appendTrailingCharsToStr(String input, char c, int count) throws InvalidInputFault {

		String st = String.format("%0$-"+count+"s", input).replace(" ","0");
		return st;
	}

	public static void enforceMandatory(String field, Object value) throws InvalidInputFault {
		if (ServicesUtil.isEmpty(value)) {
			String message = "Field=" + field + " can't be empty";
			throw new InvalidInputFault(message, null);
		}
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static Date resultAsDate(Object o) {
		//System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]" + o);
		String template = "";
		if (o instanceof Object[]) {
			template = Arrays.asList((Object[]) o).toString();
		} else {
			template = String.valueOf(o);
		}
		Date date = null;
		try {
			if(!isEmpty(template)){
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				date = formatter.parse(template);
			}
			//System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]" + o + "[template]" + template + "[date]" + date + "yyyy-MM-dd hh:mm:ss.SSS");
		} catch (ParseException e) {
			System.err.println("resultAsString ParseException" + e.getMessage());
		}
		return date;
	}

	public static Date setInitialTime(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date setEndTime(Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}


	public static String getDateDifferenceInHours(Date date) {
		long t1 = new Date().getTime();
		long t2 = date.getTime();
		long diffinHrs = (t1 - t2) / (60 * 60 * 1000) % 60;
		return String.valueOf(diffinHrs);

	}

	public static Calendar timeStampToCal(Object obj) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			//System.err.println("[PMC][ServicesUtil][timeStampToCal][obj]" + obj + "obj.toString()" + obj.toString());
			cal.setTime(formatter.parse(obj.toString()));
			return cal;
		} catch (Exception e) {
			System.err.println("[PMC][ServicesUtil][timeStampToCal][getMessage]" + e.getMessage());
		}
		return null;
	}

	public static Date dateResultAsDate(Object o) {
		String template = "";
		if (o instanceof Object[]) {
			template = Arrays.asList((Object[]) o).toString();
		} else {
			template = String.valueOf(o);
		}
		Date date = null;
		try {
			if(!isEmpty(template)){
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
				date = formatter.parse(template);
			}
			//System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]" + o + "[template]" + template + "[date]" + date + "yyyy-MM-dd hh:mm:ss.SSS");
		} catch (ParseException e) {
			System.err.println("resultAsString ParseException" + e.getMessage());
		}
		return date;
	}

	public static String getSLATimeLeft(Calendar sla) {

		Calendar cal = Calendar.getInstance();
		long today = cal.getTimeInMillis();
		long slaTime = sla.getTimeInMillis();
		long timeLeft = slaTime - today;
		if (timeLeft > 0) {
			String timeLeftString = "";
			int seconds = (int) (timeLeft / 1000) % 60;
			int minutes = (int) ((timeLeft / (1000 * 60)) % 60);
			int hours = (int) ((timeLeft / (1000 * 60 * 60)) % 24);
			int days = (int) (timeLeft / (1000 * 60 * 60 * 24));
			timeLeftString = "" + days + " days :" + hours + " hrs :" + minutes + " min :" + seconds + "sec";
			return timeLeftString;
		} else {
			return "Breach";
		}

	}

	public static String getCompletedTimePassed(Calendar sla, Calendar completedAt) {

		Calendar cal = Calendar.getInstance();
		long completed = cal.getTimeInMillis();
		long slaTime = sla.getTimeInMillis();
		long timePassed = completed - slaTime;
		String timePassesString = "";
		System.err.println("date_time sla: "+sla);
		System.err.println("date_time completedAt: "+completedAt);
		System.err.println("date_time timePassed: "+timePassed);

		int seconds = (int) (timePassed / 1000) % 60;
		int minutes = (int) ((timePassed / (1000 * 60)) % 60);
		int hours = (int) ((timePassed / (1000 * 60 * 60)) % 24);
		System.err.println("date_time hours: "+hours);
		int days = (int) (timePassed / (1000 * 60 * 60 * 24));
		System.err.println("date_time days: "+days);
		timePassesString = "" + days + " days :" + hours + " hrs :" + minutes + " min :" + seconds + "sec";
		return timePassesString;
	}

	public static float getPercntTimeCompleted(Calendar createdDate, Calendar slaDate) {
		Calendar cal = Calendar.getInstance();
		long today = cal.getTimeInMillis();
		long created = createdDate.getTimeInMillis();
		System.err.println("[PMC][ServicesUtil][timeStampToCal][today]" + today + "created" + created + "slaDate.getTimeInMillis()" + slaDate.getTimeInMillis());
		return (((float) (today - created) / (slaDate.getTimeInMillis() - created)) * 100);
	}

	public static Date getEarlierDate(int noOfDays) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -noOfDays);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String getDecryptedText(String encryptedText){
		if(!isEmpty(encryptedText)){
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
			return new String(decodedBytes);
		}
		return null;
	}

	public static String getBasicAuth(String userName ,String password) {
		String userpass = userName + ":" + password;
		return "Basic "
		+ javax.xml.bind.DatatypeConverter.printBase64Binary(userpass
				.getBytes());
	}


	public static String convertInputStreamToString(InputStream inputStream){
		try {

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[10000];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			byte[] byteArray = buffer.toByteArray();
			return new String(byteArray);

		} catch (Exception e) {
			System.err.println("[PMC][ServicesUtil][convertInputStreamToString][error]"+e.getMessage());
		}
		return null;
	}
	public static Date resultTAsDate(Object o)
	{
		Date date = null;
		if(!isEmpty(o) && o.toString() != ""){
			String template = "";
			if (o instanceof Object[]) {
				template = Arrays.asList((Object[]) o).toString();
			} else {
				template = String.valueOf(o);
			}
			try {
				DateFormat formatterT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
				DateFormat newDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

				date = newDf.parse(newDf.format(formatterT.parse(template)));
				System.err.println("formatterT.parse(template)" +formatterT.parse(template)+"newDf.format(formatterT.parse(template)"+newDf.format(formatterT.parse(template))+"newDf.parse(newDf.format(formatterT.parse(template)))"+newDf.parse(newDf.format(formatterT.parse(template))));

				System.err.println("[PMC][WorkBoxFacade][resultAsDate][o]"+o+"[template]"+template+"[date]"+date+"yyyy-MM-dd hh:mm:ss.SSS");
			} catch (Exception e) {
				System.err.println("resultTAsDate " + e.getMessage());
			}
		}
		return date;
	}
	
	public static String removeExtension(String s){			

		for(int i=0;i<s.length();i++){
			if(s.charAt(i)=='.'){
				s=s.substring(0,i);
				break;
			} }
			
		return s;
	}
}

