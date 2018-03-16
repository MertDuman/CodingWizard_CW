package codingWizard.model;

/**
 * Holds user data.
 * @author CodingWizard
 * @version 11.05.2017.
 */
public class User {
    private static String username;
    private static int points;
    private static int ranking;
    private static int courseProgression;
    private static String joinDate;
    private static boolean isAdmin;

    private User() {

    }

    public static void setUsername(String username1) {
        username = username1;
    }

    public static void setPoints(int points1) {
        points = points1;
    }

    public static void increasePoints( int points1) {
        points += points1;
    }

    public static void setRanking(int ranking1) {
        ranking = ranking1;
    }

    public static void setCourseProgression(int courseProgression1) {
        courseProgression = courseProgression1;
    }

    public static void increaseCourseProgression() {
        courseProgression++;
    }

    public static void setJoinDate(String joinDate1) {
        joinDate = joinDate1;
    }

    public static void setIsAdmin( boolean isAdmin1) {
        isAdmin = isAdmin1;
    }

    public static String getUsername() {
        return username;
    }

    public static int getPoints() {
        return points;
    }

    public static int getRanking() {
        return ranking;
    }

    public static int getCourseProgression() {
        return courseProgression;
    }

    public static String getJoinDate() {
        return joinDate;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }
}
