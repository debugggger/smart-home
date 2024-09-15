import subprocess
import paho.mqtt.client as mqtt
import os



class servMqtt:
    def __init__(self, db):
        self.client = mqtt.Client()
        self.broker_address = ""
        self.port = 1883
        self.db = db

    def on_connect(self, client, userdata, flags, rc):
        print("Соединение установлено с кодом результатa: " + str(rc))
        client.subscribe("test")  # Подписываемся на топик "test/topic"


    def on_message(self, client, userdata, message):
        print("Получено сообщение '" + str(message.payload.decode()) + "' из топика '" + message.topic + "'")

    def startBroker(self):
        current_directory = os.path.dirname(os.path.realpath(__file__))
        mosquitto_directory = os.path.join(current_directory, "mosquitto")
        os.chdir(mosquitto_directory)
        result = subprocess.run('ipconfig', stdout=subprocess.PIPE, text=True, encoding="cp866").stdout.lower()
        scan = 0
        for i in result.split('\n'):
            if 'беспроводная' in i:
                scan = 1
            if scan:
                if 'ipv4' in i:
                    self.broker_address = i.split(':')[1].strip()
                    print("ip =", self.broker_address)
        os.system('cmd /k "mosquitto -v -c conf.conf"')
        os.chdir(current_directory)

    def startClient(self):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.client.connect(self.broker_address, self.port, 60)
        self.client.loop_forever()



