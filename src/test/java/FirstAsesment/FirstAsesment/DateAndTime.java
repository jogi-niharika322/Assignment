package FirstAsesment.FirstAsesment;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateAndTime {

	public static String getCurrentDateTime() {
		DateFormat customDate = new SimpleDateFormat("MM_dd_yy_HH_mm_ss");
		Date currentDate = new Date();
		return customDate.format(currentDate);
		}
	}


