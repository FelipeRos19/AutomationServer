package fun.felipe.commands;

import fun.felipe.Server;

import java.util.Map;

public class GetAllStatusCommand {

    public GetAllStatusCommand() {
    }

    public Map<String, String> getLocateStatus() {
        return Server.getInstance().getDevices();
    }
}
