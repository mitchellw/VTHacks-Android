package com.vt.vthacks;

public class Constants {
	/**
	 * Shared preferences stuff.
	 */
	public static final String PREFS = "VTHacks";
	public static final String PREFS_FIRST_LAUNCH = "firstLaunch";
	public static final String PREFS_GCM_ID = "gcmID";
	public static final String PREFS_SNS_ARN = "snsARN";
	public static final String PREFS_AWS_SECRET_ACCESS_KEY = "secretAccessKey";
	public static final String PREFS_AWS_SECURITY_TOKEN = "securityToken";
	public static final String PREFS_AWS_EXPIRATION = "expiration";
	public static final String PREFS_AWS_ACCESS_KEY_ID = "accessKeyID";
	public static final String PREFS_AWS_REGISTERED = "registered";
	
	/**
	 * AWS stuff.
	 */
	public static final String AWS_TOPIC_NAME = "VTHacksTopic";
	public static final String AWS_QUEUE_NAME = "VTHacksQueue";
	public static final String AWS_PLATFORM_APPLICATION_ARN = "arn:aws:sns:us-east-1:860000342007:app/GCM/VTHacksAndroid";
	public static final String AWS_PROTOCOL = "application";
	public static final String AWS_TOPIC_ARN = "arn:aws:sns:us-east-1:860000342007:VTHacksTopic";
	public static final String AWS_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/860000342007/VTHacksQueue";
	public static final String AWS_QUEUE_ARN = "arn:aws:sqs:us-east-1:860000342007:VTHacksQueue";
	
	/**
	 * Push Notification stuff.
	 */
	public static final int PUSH_NOTIFICATION_RECEIVED = 5;
}
