int E1 = 5;     //M1 Speed Control
int E2 = 6;     //M2 Speed Control
int M1 = 4;    //M1 Direction Control
int M2 = 7;    //M1 Direction Control
#define shortSensor A0
#define longSensor A1
int sensorSValue, sensorLValue, cmS, cmL;

void setup() {
  int i;
  for (i = 4; i <= 7; i++) {
    pinMode(i, OUTPUT);
  }
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(13, OUTPUT);
  Serial.begin(115200);      //Set Baud Rate
  Serial.setTimeout(100);
  Serial.println("Start");

}
void advance(int a)         //Move forward
{
  analogWrite (E1, a);     //PWM Speed Control
  digitalWrite(M1, HIGH);
  analogWrite (E2, a);
  digitalWrite(M2, LOW);
}
void turn_L (int a)            //Turn Left
{
  analogWrite (E1, a);
  digitalWrite(M1, HIGH);
  analogWrite (E2, a);
  digitalWrite(M2, HIGH);
}
void turn_R (int a)            //Turn Right
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, a);
  digitalWrite(M2, LOW);
}
void back_off (int a)         //Move backward
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, a);
  digitalWrite(M2, HIGH);
}
void leftSpeed (int a)         //MChange speed on lef-side
{
  analogWrite (E2, a);
  digitalWrite(M2, LOW);
}
void rightSpeed (int a)         //MChange speed on lef-side
{
  analogWrite (E1, a);
  digitalWrite(M1, HIGH);
}
void shortSensorOutput()
{
  // read voltage of short sensor and convert to cm
  int in1 = analogRead(shortSensor);
  delay(100);
  int in2 = analogRead(shortSensor);
  delay(100);
  int in3 = analogRead(shortSensor);
  if ((abs(in2 - in1) <= 5) && (abs(in3 - in2) <= 5)) {
    sensorSValue = (in1 + in2 + in3) / 3;
  }
  cmS = pow((sensorSValue * 0.0048828125), -1) * 13;

}

void longSensorOutput()
{
  // read voltage of long sensor and convert to cm
  int in1 = analogRead(longSensor);
  delay(100);
  int in2 = analogRead(longSensor);
  delay(100);
  int in3 = analogRead(longSensor);
  if ((abs(in2 - in1) <= 5) && (abs(in3 - in2) <= 5)) {
    sensorLValue = (in1 + in2 + in3) / 3;
  }
  cmL = 10650.08 * pow(sensorLValue, -0.935) - 10;
}


void loop() {

  if (Serial.available() > 0) {
    String val = Serial.readStringUntil(' ');
    //Serial.println("Input:" + val);
    if (val.startsWith("w")) { // Move forward
      val.remove(0, 1);
      advance(val.toInt());
    } else if (val.startsWith("a")) { //Turn left
      val.remove(0, 1);
      turn_L(val.toInt());
    } else if (val.startsWith("d")) { //Turn right
      val.remove(0, 1);
      turn_R(val.toInt());
    } else if (val.startsWith("s")) { //Move backwards
      val.remove(0, 1);
      back_off(val.toInt());
    } else if (val.startsWith("l")) {
      val.remove(0, 1);
      leftSpeed(val.toInt());
    } else if (val.startsWith("r")) {
      val.remove(0, 1);
      rightSpeed(val.toInt());
    } else if(val.equals("vss")){
      Serial.println(cmS);
      //Serial.println(val);
    } else if(val.equals("vls")){
      Serial.println(cmL);
    } else{
       Serial.print("no commands: ");
       Serial.println(val);
    }
  } //end if
  //end loop
  // Continually update distance of short sensor
  shortSensorOutput();
  // Continually update distance of long sensor
  longSensorOutput();


}
