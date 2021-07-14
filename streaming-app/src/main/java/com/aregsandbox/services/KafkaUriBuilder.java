package com.aregsandbox.services;

public class KafkaUriBuilder {

    private static final String ROOT_TMPL = "kafka:%s?";
    private static final String DELIM = "&";
    private static final String NO_PREFIX = "";
    private static final String ADDT_PREFIX = "additionalProperties.";

    private StringBuilder tmpl;
    private boolean first = true;

    public static KafkaUriBuilder create(String topicName) {
        String root = String.format(ROOT_TMPL, topicName);
        KafkaUriBuilder kub = new KafkaUriBuilder(root);
        return kub;
    }

    private KafkaUriBuilder(String root) {
        tmpl = new StringBuilder();
        tmpl.append(root);
        first = true;
    }

    public KafkaUriBuilder appendProperty(String key, String value) {
        return append(NO_PREFIX, key, value);
    }

    public KafkaUriBuilder appendAdditional(String key, String value) {
        return append(ADDT_PREFIX, key, value);
    }

    private KafkaUriBuilder append(String prefix, String key, String value) {
        if(!first) {
            tmpl.append(DELIM);    
        }
        tmpl.append(prefix);
        tmpl.append(key);
        tmpl.append("=");
        tmpl.append(value);
        
        first = false;

        return this;
    }

    public String value() {
        return tmpl.toString();
    }
}
