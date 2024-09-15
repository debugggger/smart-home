#ifndef HUMSENSOR_H
#define HUMSENSOR_H

class HumSensor {
private:
    char name;
    int val;
    int port;
public:
    HumSensor getHumSensor();
}

#endif