package sortingbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;
import java.util.ArrayList;

public class ArduinoCommunication implements SerialPortEventListener {

    SerialPort serialPort;

    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {
        "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Odroid
        "/dev/ttyACM1", // Odroid
        "/dev/ttyUSB0", // Linux
        "COM5", // Windows
        "/dev/cu.usbmodem1411",// Mac
        "/dev/cu.usbmodem1421"// Mac
    };
    /**
     * A BufferedReader which will be fed by a InputStreamReader converting the
     * bytes into characters making the displayed results codepage independent
     */
    private static BufferedReader input;
    /**
     * The output stream to the port
     */
    private static OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 115200;
    // ArrayList for storing inputs fro arduino
    private ArrayList<String> inputList = new ArrayList<>();

    public void initialize() {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
           // System.exit(1);
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    // sends commands that is not supposed to get a response
    public synchronized void sendCommand(String aText) {
        try {
            System.out.println(aText);
            output.write(aText.getBytes());
        } catch (Exception e) {
        }

    }
    //sends commands and returns the response that is collected in the input list
    public synchronized String getInput(String aText) {
        try {
            output.write(aText.getBytes());
        } catch (Exception e) {
        }
        while (inputList.isEmpty()) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        String inputA = inputList.get(0);
        inputList.remove(0);
        return inputA;
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                inputList.add(input.readLine());
//                System.out.print("Gotinput!: ");
//                System.out.println(inputList.toString());
                notifyAll();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
// Ignore all the other eventTypes, but you should consider the other ones.

    }

    public ArduinoCommunication() {
        initialize();

    }
}
