package fun.felipe.commands;

import fun.felipe.Server;

public class GetStatusCommand {
    private final String deviceID;

    public GetStatusCommand(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getStatus() {
        return Server.getInstance().getDevices().get(this.deviceID);
    }
}
