#include "inc/stepper.h"

Stepper::Stepper(int port_){
    port = port_;
}


void Stepper::setName(char name_){
    name = name_;
}

void Stepper::move(int steps, int speed){
    
}
