package codingWizard.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

/**
 * Retrieves data from the database.
 * @author CodingWizard
 * @version 11.05.2017.
 */
public class DataSource {

    public static final String DB_NAME = "user.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/CodingWizardReal/" + DB_NAME;
    public static final String SHORTER_CONN_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_USERINFO = "userinfo";
//    public static final String TABLE_RANKINGS = "rankings";

    private static Connection conn;

    /**
     * Retrieves the current lecture with the id.
     * @param id Lecture id.
     * @return The lecture.
     */
    public static Lecture getLecture( int id) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery( "SELECT * FROM lectures WHERE lectures.id = " + id)){

            Lecture lecture = new Lecture();
            lecture.setId( results.getInt( 1));
            lecture.setTitle( results.getString( 2));
            lecture.setExplanation( results.getString( 3));
            lecture.setAssignment( results.getString( 4));
            lecture.setAnswer( results.getString( 5));
            lecture.setPoints( results.getInt( 6));

            return lecture;

        } catch ( SQLException e) {
            System.out.println( "Couldn't get lecture.");
            return null;
        }
    }

    /**
     * Checks if the user exists in the database.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return a boolean value.
     */
    public static boolean isUserExists( String username, String password) {
        Statement statement = null;
        ResultSet results = null;
        try {
            statement = conn.createStatement();
            statement.execute( "SELECT count(*) FROM " + TABLE_USERS + " WHERE username = '" + username + "' AND password = '" + password + "'");
            results = statement.getResultSet();

            return results.getInt( 1) != 0;
        } catch ( SQLException e) {
            System.out.println( "Could not search user exists.");
            return false;
        } finally {
            try {
                results.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }

            try {
                statement.close();
            } catch ( SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds user to the users database.
     * @param username The user's username
     * @param password The user's password.
     * @return a boolean value
     */
    public static boolean addUserToUsers( String username, String password) {
        try (Statement statement = conn.createStatement()){
            statement.execute( "INSERT INTO " + TABLE_USERS + " VALUES ('" + username + "', '" + password + "')");
            handleNewUserInfo( username);
        } catch ( SQLException e) {
            System.out.println( "Could not add user to users.");
            return false;
        }

        return true;
    }

    /**
     * When a new user enters, handles his/her info i.e. the join date.
     * @param username The username
     * @return a boolean value.
     */
    private static boolean handleNewUserInfo( String username) {
        LocalDate date = LocalDate.now();

        try (Statement statement = conn.createStatement()){
            statement.execute( "INSERT INTO " + TABLE_USERINFO + " VALUES ('" + username + "', 0, 0, 1, '" + date.toString() + "')");
            return true;
        } catch ( SQLException e) {
            System.out.println( "Could not handle new user info.");
            return false;
        }
    }

    /**
     * Updates the user's info from the database.
     * @param username The username of the user.
     * @return a boolean value.
     */
    public static boolean handleUserLogin( String username) {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery( "SELECT * FROM userinfo WHERE userinfo.username = '" + username + "'")){

            User.setUsername( results.getString( 1));
            User.setPoints( results.getInt( 2));
            User.setRanking( results.getInt( 3));
            User.setCourseProgression( results.getInt( 4));
            User.setJoinDate( results.getString( 5));

            if ( username.equals( "mert") || username.equals( "ilke") || username.equals( "oguz") || username.equals( "talha") || username.equals( "ömer")) {
                User.setIsAdmin( true);
                User.setCourseProgression( 13);
                updateUser( User.getUsername(), User.getPoints(), User.getRanking(), User.getCourseProgression());
            } else {
                User.setIsAdmin( false);
            }

            return true;

        } catch ( SQLException e) {
            System.out.println( "Could not handle user login.");
            return false;
        }
    }

    /**
     * Querys the user rankings from the database.
     * @return The observable list of the user's rankings.
     */
    public static ObservableList<RankingContent> queryUserRanking() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery( "SELECT userinfo.username, userinfo.points FROM userinfo ORDER BY userinfo.points DESC")){
            ObservableList<RankingContent> rankingList = FXCollections.observableArrayList();
            ResultSetMetaData metaData = results.getMetaData();

            int counter = 1;

            while ( results.next()) {
                RankingContent rc = new RankingContent();
                rc.setRank( counter);
                rc.setName( results.getString( 1));
                if ( rc.getName().equals( User.getUsername())) {
                    User.setRanking( counter);
                }
                rc.setPoints( results.getInt( 2));
                rankingList.add( rc);
                counter++;
            }

            return rankingList;

        } catch ( SQLException e) {
            System.out.println( "Could not query user ranking.");
            return null;
        }
    }

    /**
     * Updates the user with the parameters.
     * @param username The username.
     * @param points The points.
     * @param ranking The ranking.
     * @param courseProgression The course progression.
     */
    public static void updateUser( String username, int points, int ranking, int courseProgression) {
        try (Statement statement = conn.createStatement()){
            statement.execute( "UPDATE userinfo SET points=" + points + " WHERE username = '" + username + "'");
            statement.execute( "UPDATE userinfo SET ranking=" + ranking + " WHERE username = '" + username + "'");
            statement.execute( "UPDATE userinfo SET courseprogression=" + courseProgression + " WHERE username = '" + username + "'");
        } catch ( SQLException e) {
            System.out.println( "Couldn't update user.");
        }
    }

    /**
     * Makes a connection to the database.
     * @return a boolean value.
     */
    public static boolean open() {
        try {
            conn = DriverManager.getConnection(SHORTER_CONN_STRING);
            return true;
        } catch ( SQLException e) {
            System.out.println( "Could not connect to database.");
            return false;
        }
    }

    /**
     * Closes the database.
     */
    public static void close() {
        try {
            conn.close();
        } catch ( SQLException e) {
            System.out.println( "Could not close the database.");
        }
    }
}
