import java.util.*;

public class gr{
	public static int randInt(int min, int max) {

    // NOTE: Usually this should be a field rather than a method
    // variable so that it is not re-seeded every call.
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}

	public static void main(String[] args){
		long time = 1420000000;
		for(int i=0;i<200;i++){
			BugReportDatabase.createNewReportTime("test","title","desc",time);
			time = time+(200*randInt(1,(int)(10+0.01*Math.pow(100-i,2))));
		}
		
		ArrayList<Integer> ids = BugReportDatabase.getBugReportIds();
		for(int i=0;i<ids.size();i++){
			if(randInt(1,100) < (int)(70+0.0008*Math.pow(i,2))){
				BugReport report = BugReportDatabase.getBugReport(ids.get(i));
				report.submitNewVersionTime("test","desc",true,report.getTime(report.getFirstVersion()) + randInt(1000,40000));
			}
		}
	}
}