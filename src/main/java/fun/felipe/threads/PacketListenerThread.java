package fun.felipe.threads;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.felipe.Server;
import fun.felipe.commands.GetAllStatusCommand;
import fun.felipe.commands.GetStatusCommand;
import fun.felipe.commands.SetStatusCommand;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

public class PacketListenerThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Iniciando Thread de Processamento de Comandos!");
        try (DatagramSocket socket = new DatagramSocket(Server.getInstance().getPort())) {
            byte[] receivedData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivePacket);

                String jsonRequest = new String(receivePacket.getData(), 0, receivePacket.getLength());
                JsonObject request = JsonParser.parseString(jsonRequest).getAsJsonObject();

                String command = request.get("command").getAsString();
                String deviceID = "";
                
                if (!command.equals("get_all"))
                    deviceID = request.get("locate").getAsString();

                System.out.println("Executando o Comando: " + command);

                JsonObject response = new JsonObject();

                switch (command) {
                    case "get" -> {
                        response.addProperty("locate", deviceID);
                        response.addProperty("status", new GetStatusCommand(deviceID).getStatus());
                    }
                    case "set" -> {
                        String status = request.get("value").getAsString();
                        response.addProperty("locate", deviceID);
                        response.addProperty("status", new SetStatusCommand(deviceID, status).updateStatus());
                    }
                    case "get_all" -> {
                        JsonArray devicesArray = new JsonArray();
                        for (Map.Entry<String, String> devices : new GetAllStatusCommand().getLocateStatus().entrySet()) {
                            JsonObject device = new JsonObject();
                            device.addProperty("locate", devices.getKey());
                            device.addProperty("status", devices.getValue());
                            devicesArray.add(device);
                        }

                        response.add("locates", devicesArray);
                    }
                    default -> response.addProperty("error", "Command not Found!");
                }

                byte[] buffer = response.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(packet);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
