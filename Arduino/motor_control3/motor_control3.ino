int E1 = 5;     //M1 Speed Control
int E2 = 6;     //M2 Speed Control
int M1 = 4;    //M1 Direction Control
int M2 = 7;    //M1 Direction Control

void setup() {
  int i;
  for (i = 4; i <= 7; i++)
    pinMode(i, OUTPUT);
  Serial.begin(19200);      //Set Baud Rate
  Serial.println("Run keyboard control");

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
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
  digitalWrite(M2, LOW);
}
void turn_R (int a, int b)            //Turn Right
{
  analogWrite (E1, a);
  digitalWrite(M1, HIGH);
  analogWrite (E2, b);
  digitalWrite(M2, HIGH);
}
void back_off (int a, int b)         //Move backward
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
  digitalWrite(M2, HIGH);
}

void loop() {

  if (Serial.available() > 0) {
    String val = Serial.readString();
    Serial.println("Input:" + val);
    if (val.equals("w")) { // Move forward
      bool waiting = true;
      while (waiting) {
        if (Serial.available() > 0) {
          long sp = Serial.readString().toInt();
          Serial.println("Speed");
          Serial.println(sp);
          advance(sp, sp);
          waiting = false;
        } else waiting = true; //end speed if
      } //end while
    } else if (val.equals("a")) { //Turn left
      bool waiting = true;
      while (waiting) {
        if (Serial.available() > 0) {
          long sp = Serial.readString().toInt();
          Serial.println("Speed");
          Serial.println(sp);
          turn_L(sp, sp);
          waiting = false;
        } else waiting = true; // end speed if
      } //end while
    } else if (val.equals("d")) { //Turn right
      bool waiting = true;
      while (waiting) {
        if (Serial.available() > 0) {
          long sp = Serial.readString().toInt();
          Serial.println("Speed");
          Serial.println(sp);
          turn_R(sp, sp);
          waiting = false;
        } else waiting = true; // end speed if
      } //end while
    } else if (val.equals("s")) { //Move backwards
      bool waiting = true;
      while (waiting) {
        if (Serial.available() > 0) {
          long sp = Serial.readString().toInt();
          Serial.println("Speed");
          Serial.println(sp);
          back_off(sp, sp);
          waiting = false;
        } else waiting = true; // end speed if
      } //end while
    }
  } //end first if
  //end loop
 }
