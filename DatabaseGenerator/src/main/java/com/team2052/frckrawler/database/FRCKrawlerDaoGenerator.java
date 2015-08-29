package com.team2052.frckrawler.database;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class FRCKrawlerDaoGenerator {
    public static final String jsonPropertyConverter = "com.team2052.frckrawler.database.converters.JsonPropertyConverter";
    public static final String jsonElementType = "com.google.gson.JsonElement";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.team2052.frckrawler.db");

        Entity game = schema.addEntity("Game");
        Entity event = schema.addEntity("Event");
        Entity user = schema.addEntity("User");
        Entity metric = schema.addEntity("Metric");
        Entity match = schema.addEntity("Match");
        Entity matchData = schema.addEntity("MatchData");
        Entity matchComment = schema.addEntity("MatchComment");
        Entity robot = schema.addEntity("Robot");
        Entity robotEvent = schema.addEntity("RobotEvent");
        Entity pitData = schema.addEntity("PitData");

        //Match data
        matchData.implementsSerializable();
        matchData.addIdProperty();

        Property match_data_event_id = matchData.addLongProperty("event_id").notNull().getProperty();
        matchData.addToOne(event, match_data_event_id);

        Property match_data_robot_id = matchData.addLongProperty("robot_id").notNull().getProperty();
        matchData.addToOne(robot, match_data_robot_id);

        Property match_data_user_id = matchData.addLongProperty("user_id").getProperty();
        matchData.addToOne(user, match_data_user_id);

        Property match_data_metric_id = matchData.addLongProperty("metric_id").notNull().getProperty();
        matchData.addToOne(metric, match_data_metric_id);

        matchData.addIntProperty("match_type").notNull().getProperty();
        matchData.addLongProperty("match_number").notNull();
        matchData.addDateProperty("last_updated").notNull();
        matchData.addStringProperty("data").customType(jsonElementType, jsonPropertyConverter);

        //Pit data
        pitData.implementsSerializable();
        pitData.addIdProperty();
        Property pit_data_robot_id = pitData.addLongProperty("robot_id").notNull().getProperty();
        Property pit_data_metric_id = pitData.addLongProperty("metric_id").notNull().getProperty();
        Property pit_data_event_id = pitData.addLongProperty("event_id").notNull().getProperty();
        Property pit_data_user_id = pitData.addLongProperty("user_id").getProperty();
        pitData.addStringProperty("data").customType(jsonElementType, jsonPropertyConverter);
        pitData.addToOne(event, pit_data_event_id);
        pitData.addToOne(robot, pit_data_robot_id);
        pitData.addToOne(user, pit_data_user_id);
        pitData.addToOne(metric, pit_data_metric_id);


        //Match Comment
        matchComment.implementsSerializable();
        matchComment.addIdProperty().autoincrement().unique();
        matchComment.addLongProperty("match_number");
        matchComment.addIntProperty("game_type");
        Property match_comment_robot_id = matchComment.addLongProperty("robot_id").getProperty();
        Property match_comment_event_id = matchComment.addLongProperty("event_id").getProperty();
        matchComment.addStringProperty("comment");

        //Robot Event
        robotEvent.implementsSerializable();
        robotEvent.addIdProperty().unique().autoincrement();
        Property robot_event_robot_id = robotEvent.addLongProperty("robot_id").notNull().getProperty();
        Property robot_event_event_id = robotEvent.addLongProperty("event_id").notNull().getProperty();
        robotEvent.addStringProperty("data");

        //Match
        match.implementsSerializable();
        match.addIdProperty().autoincrement().unique();
        match.addIntProperty("number");
        match.addStringProperty("key").unique();
        Property match_event_id = match.addLongProperty("event_id").notNull().getProperty();
        match.addStringProperty("data");
        match.addStringProperty("type");

        //Events
        event.addIdProperty().autoincrement().unique();
        event.implementsSerializable();
        event.addStringProperty("name");
        event.addStringProperty("fmsid").unique();
        Property event_game_id = event.addLongProperty("game_id").notNull().getProperty();
        event.addToMany(robotEvent, robot_event_event_id);
        event.addToMany(match, match_event_id);
        event.addToMany(matchData, match_data_event_id);
        event.addToMany(pitData, pit_data_event_id);
        event.addToMany(matchComment, match_comment_event_id);
        event.addStringProperty("data");
        event.addDateProperty("date");

        //Robots
        robot.implementsSerializable();
        robot.addIdProperty().unique().autoincrement();
        Property robot_team_id = robot.addLongProperty("team_id").notNull().getProperty();
        Property robot_game_id = robot.addLongProperty("game_id").notNull().getProperty();
        robot.addToMany(robotEvent, robot_event_robot_id);
        robot.addToMany(matchData, match_data_robot_id);
        robot.addToMany(pitData, pit_data_robot_id);
        robot.addToMany(matchComment, match_comment_robot_id);
        robot.addStringProperty("data");
        robot.addStringProperty("comments");

        //Metric
        metric.implementsSerializable();
        metric.addIdProperty().unique().autoincrement();
        metric.addStringProperty("name");
        metric.addIntProperty("category");
        metric.addToMany(matchData, match_data_metric_id);
        metric.addToMany(pitData, pit_data_metric_id);
        metric.addIntProperty("type");
        metric.addStringProperty("data");
        Property metric_game_id = metric.addLongProperty("game_id").notNull().getProperty();

        //Games
        game.implementsSerializable();
        game.addIdProperty().autoincrement().unique();
        game.addToMany(event, event_game_id);
        game.addToMany(robot, robot_game_id);
        game.addToMany(metric, metric_game_id);
        game.addStringProperty("name");

        //Team
        Entity team = schema.addEntity("Team");
        team.implementsSerializable();
        team.addLongProperty("number").unique().primaryKey();
        team.addStringProperty("teamkey").unique();
        team.addStringProperty("name");
        team.addToMany(robot, robot_team_id);
        team.addStringProperty("data");

        //Not used, but still is wanted.
        Entity robotPhoto = schema.addEntity("RobotPhoto");
        robotPhoto.implementsSerializable();
        robotPhoto.addIdProperty().autoincrement().unique();
        robotPhoto.addStringProperty("location");
        robotPhoto.addToOne(robot, robotPhoto.addLongProperty("robot_id").getProperty());
        robotPhoto.addStringProperty("title");
        robotPhoto.addDateProperty("date");

        //User
        user.implementsSerializable();
        user.addIdProperty().autoincrement().unique();
        user.addToMany(matchData, match_data_user_id);
        user.addToMany(pitData, pit_data_user_id);
        user.addStringProperty("name");

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
