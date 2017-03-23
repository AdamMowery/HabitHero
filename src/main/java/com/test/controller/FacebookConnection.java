package com.test.controller;

import java.util.Map;

/**
 * Created by adamm on 3/22/2017.
 */
public class FacebookConnection {

    private String code;
    private String out;
    private String id;
    private String email;

    FacebookConnection(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOut() {
        return out;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    // finishes the connection to get the user info
    FacebookConnection invoke() {
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);
        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

        id = fbProfileData.get("id");
        out = fbProfileData.get("name");
        email = fbProfileData.get("email");

        return this;
    }

}
