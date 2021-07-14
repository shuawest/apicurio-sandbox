package com.aregsandbox.config;


public class DestinationConfig {
    private String destination = "kafka"; 
    private String topicName;

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

}
