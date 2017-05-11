
public class DateTime {
	
	int SHour = 0;
	int SMinute = 0;
	int EHour = 0;
	int EMinute = 0;
	int dayInt = 0;
	String day = "";
  	
	public DateTime(int SH, int SM, int EH, int EM, int D){
		SHour = SH;
		SMinute = SM;
		EHour = EH;
		EMinute = EM;
		dayInt = D;
		
		switch(dayInt){
			case 1: day = "Sunday"; break;
			case 2: day = "Monday"; break;
			case 3: day = "Tuesday"; break;
			case 4: day = "Wednesday"; break;
			case 5: day = "Thursday"; break;
			case 6: day = "Friday"; break;
			case 7: day = "Saturday"; break;
			default: day = "Unspecified";
		}
	}
	
	public String toString(){
		return "Start: " + SHour + ":" + SMinute + " End: " + EHour + ":" + EMinute + " Day: " + day;
	}
}
