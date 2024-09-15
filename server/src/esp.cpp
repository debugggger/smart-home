#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

#include "include/eeprom.h"
#include "inc/wifi.h"
#include "inc/connection.h"



void setup() {
    Serial.begin(9600);

    WiFiManager wifiManager;

    wifiManager.setSSID(readStringFromEEPROM(SSID_NAME_ADDR));
    wifiManager.setPassword(readStringFromEEPROM(SSID_PASS_ADDR));
    wifiManager.connect();
    if (wifiManager.isConnected()) {
        Serial.println("Устройство подключено к Wi-Fi.");
    } else {
        Serial.println("Не удалось подключиться к Wi-Fi.");
    }


    MqttClient mqttClient(wifiManager.getWiFiClient());
    mqttClient.setServer(readStringFromEEPROM(MQTT_ADDR), 1883);
    
    mqttClient.setCallback([](char* topic, byte* payload, unsigned int length) {
        mqttClient.parse(payload);
        Serial.print("Получено сообщение на тему: ");
        Serial.println(topic);
    });

    if (mqttClient.connect("client_id")) {
        mqttClient.loop();
    }

}

void loop() {
    for (int i = 0; i< buttonsCount; i++){
      if (buttons[i].getState() != buttons[i].getPrevState())
    }

    if (!client.connected()) 
  {
    reconnect();
  }
  delay(2000);
}



void initDevices(){

}

void reconnect() 
{
  while (!client.connected()) 
  {
    if (client.connect("ESP8266_Client"))
    {
      client.subscribe("test");
    } 
    else 
    {
      delay(1000);
      Serial.print('.');
    }
  }
}

void getSettings(){

}
