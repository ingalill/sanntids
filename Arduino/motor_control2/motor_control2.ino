//Standard DLL Speed control

int E1 = 5;     //M1 Speed Control
int E2 = 6;     //M2 Speed Control
int M1 = 4;    //M1 Direction Control
int M2 = 7;    //M1 Direction Control

///For previous Romeo, please use these pins.
/*int E1 = 6;     //M1 Speed Control
  int E2 = 9;     //M2 Speed Control
  int M1 = 7;    //M1 Direction Control
  int M2 = 8;    //M1 Direction Control*/

void stop()                    //Stop
{
  digitalWrite(E1, LOW);
  digitalWrite(E2, LOW);
}
void advance(char a, char b)         //Move forward
{
  analogWrite (E1, a);     //PWM Speed Control
  digitalWrite(M1, HIGH);
  analogWrite (E2, b);
  digitalWrite(M2, LOW);
}
void back_off (char a, char b)         //Move backward
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
  digitalWrite(M2, HIGH);
}
void turn_L (char a, char b)            //Turn Left
{
  analogWrite (E1, a);
  digitalWrite(M1, LOW);
  analogWrite (E2, b);
  digitalWrite(M2, LOW);
}
void turn_R (char a, char b)            //Turn Right
{
  analogWrite (E1, a);
  digitalWrite(M1, HIGH);
  analogWrite (E2, b);
  digitalWrite(M2, HIGH);
}
void setup()
{
  int i;
  for (i = 4; i <= 7; i++)
    pinMode(i, OUTPUT);
  Serial.begin(19200);      //Set Baud Rate
  Serial.println("Run keyboard control");
}
int getSpeed ()            //Turn Right
{
  int sp = 0;
  if (Serial.available()) {
    char val = Serial.read();
    Serial.println("Speed value:" + val);
    if (val != -1) {
      Serial.println(sp);
    }
  }
  return sp;
}
void loop()
{

  if (Serial.available()) {
    char val = Serial.read();
    //int sp = 0;
    if (val != -1)
    {

      switch (val)
      {
        case 'w'://Move Forward
          /*while(!Serial.available() || (sp==0)){
            sp=getSpeed();
            }*/
          Serial.println("Input:"+val);
          advance (50, 50);  //move forward in max speed
          break;
        case 's'://Move Backward
          back_off (50, 50);  //move back in max speed
          break;
        case 'a'://Turn Left
          turn_L (50, 50);
          break;
        case 'd'://Turn Right
          turn_R (50, 50);
          break;
        case 'z':
          Serial.println("Hello");
          break;
        case 'x':
          stop();
          break;
      }
    }
    else stop();
  }
}
