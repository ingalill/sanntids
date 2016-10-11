int E1 = 5;     //M1 Speed Control
int E2 = 6;     //M2 Speed Control
int M1 = 4;    //M1 Direction Control
int M2 = 7;    //M1 Direction Control
#define shortSensor A0
#define longSensor A1
float sensorSValue, sensorLValue, cmS, cmL;

void setup() {
  int i;
  for (i = 4; i <= 7; i++){
     pinMode(i, OUTPUT);
  }
  pinMode(A0,INPUT);
  pinMode(A1,INPUT);
  Serial.begin(19200);      //Set Baud Rate
  Serial.println("Start");

}
void advance(int a, int b)         //Move forward
{
  analogWrite (E1, a);     //PWM Speed Control
  digitalWrite(M1, HIGH);
  analogWrite (E2, b);
  digitalWrite(M2, LOW);
}
void turn_L (int a, int b)            //Turn Left
{
  analogWrite (E1, a);
  digitalWrite(M1, HIGH);
  analogWrite (E2, b);
  digitalWrite(M2, HIGH);
}
void turn_R (int a, int b)            //Turn Right
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
  digitalWrite(M2, LOW);
}
void back_off (int a, int b)         //Move backward
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
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

void loop() {

  if (Serial.available() > 0) {
    String val = Serial.readString();
    //Serial.println("Input:" + val);
    if (val.startsWith("w")) { // Move forward
      val.remove(0,1);
      advance(val.toInt(),val.toInt());
    } else if (val.startsWith("a")) { //Turn left
       val.remove(0,1);
       turn_L(val.toInt(),val.toInt());
    } else if (val.startsWith("d")) { //Turn right
       val.remove(0,1);
       turn_R(val.toInt(),val.toInt());
    } else if (val.startsWith("s")) { //Move backwards
       val.remove(0,1);
       back_off(val.toInt(),val.toInt());
    } else if (val.startsWith("l")) {
      val.remove(0,1);
      leftSpeed(val.toInt());
    }else if (val.startsWith("r")) {
      val.remove(0,1);
      rightSpeed(val.toInt());
    } else if(val.equals("vss")){
      // if input equals vss(voltage short sensor), read voltage
      sensorSValue=analogRead(shortSensor)*0.0048828125;
      cmS=pow(sensorSValue,-1)*13;
      delay(100);
      Serial.println("cm: ");
      Serial.println(cmS);
    } else if(val.equals("vls")){
      // if input equals vls(voltage long sensor), read voltage
      sensorLValue=analogRead(longSensor);
      cmL=10650.08*pow(sensorLValue,-0.935)-10;
      delay(100);
      Serial.println("cm: ");
      Serial.println(cmL);
    }
  } //end if
  //end loop
 }
