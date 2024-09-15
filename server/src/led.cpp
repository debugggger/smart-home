#include "inc/led.h"

Led::Led(int port_){
    port = port_;
}

LED_STATE Led::getState(){
    return state;
}

void Led::setName(char name_){
    name = name_;
}

void Led::startLed(int type_){
    
}

void offLed(){

    state = led_off;
}
