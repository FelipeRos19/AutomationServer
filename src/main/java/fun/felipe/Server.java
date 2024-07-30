package fun.felipe;

import fun.felipe.threads.PacketListenerThread;

import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Server instance;
    private final int port;
    private final Map<String, String> devices;

    private void warmup() {
        devices.put("luz_guarita", "off");
        devices.put("ar_guarita", "off");
        devices.put("luz_estacionamento", "off");
        devices.put("luz_galpao externo", "off");
        devices.put("luz_galpao interno", "off");
        devices.put("luz_escritorios", "off");
        devices.put("ar_escritorios ", "off");
        devices.put("luz_sala_reunioes", "off");
        devices.put("ar_sala_reunioes", "off");
    }

    public Server() {
        this.port = 45000;
        this.devices = new HashMap<>();
        this.warmup();
        System.out.println("Iniciando o Servidor...");
    }

    public static void main(String[] args){
        instance = new Server();
        new PacketListenerThread().run();
    }

    public static Server getInstance() {
        return instance;
    }

    public int getPort() {
        return port;
    }

    public Map<String, String> getDevices() {
        return this.devices;
    }
}