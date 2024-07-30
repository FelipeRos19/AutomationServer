package fun.felipe.commands;

import fun.felipe.Server;

public class SetStatusCommand {
    private final String deviceID;
    private final String status;

    public SetStatusCommand(String deviceID, String status) {
        this.deviceID = deviceID;
        this.status = status;
    }

    public String updateStatus() {
        Server.getInstance().getDevices().put(this.deviceID, this.status);
        return Server.getInstance().getDevices().get(this.deviceID);
    }
}
