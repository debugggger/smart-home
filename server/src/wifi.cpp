#include "wifi.h"


WiFiManager::WiFiManager() {
    ssid = "";
    password = "";
}

bool WiFiManager::connect() {
    WiFi.begin(ssid.c_str(), password.c_str());
    Serial.print("Подключение к Wi-Fi");

    unsigned long startTime = millis(); 
    const unsigned long timeout = 10000; 

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");

        
        if (millis() - startTime >= timeout) {
            Serial.println("\nОшибка: Время подключения превышено!");
            return false; 
        }
    }

    wifiClient = wifiManager.getWiFiClient();
    Serial.println("\nПодключено к сети: " + ssid);
    return true;
}

void WiFiManager::setSSID(const String &newSSID) {
    ssid = newSSID;
}

void WiFiManager::setPassword(const String &newPassword) {
    password = newPassword;
}

String WiFiManager::getSSID() const {
    return ssid;
}

String WiFiManager::getPassword() const {
    return password;
}

bool WiFiManager::isConnected() const {
    return WiFi.status() == WL_CONNECTED;
}

WiFiClient& WiFiManager::getWiFiClient() {
    return wifiClient;
}
