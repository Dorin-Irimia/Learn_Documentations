# 📌 Project: Audible Warning Circuit

This project consists of the design and implementation of an audible warning circuit, carried out together with its simulation and physical development.

## 🖥️ Features
- Door status check
- Seat belt detection
- Headlight status monitoring

## 🚀 Technologies and tools used
- **Proteus** for electrical circuit simulation, source code generation and PCB design
- **PIC16F887 microcontroller**
- Hardware components: 5 buttons, 3 LEDs, a buzzer, 8 MHz internal oscillator

## 🔧 Technical details
### Hardware components
- **Ports used:**
- Port B: configured as input for buttons
- Port A: configured as output for LEDs
- Port RC2: used to generate PWM signal
- **DOOR_SENSOR button:** connected to RB3
- **Buzzer:** set to a frequency of approximately 1kHz

### Main functions
1. **doorCheck()**: Checks the status of the doors.
2. **beltCheck()**: Monitors the seat belt.
3. **lightCheck()**: Detects the status of the headlights when the power is off.

### Interrupt routine
- Timer0 configured with a 1:8 prescaler
- Generate a 10ms delay by incrementing a variable every 1ms.

## 📝 Instructions for running
1. Clone the repository:
```bash
git clone https://github.com/utilizator/circuit-avertizare.git
```
2. Open the project in Proteus for simulation.
3. Compile and upload the code to the PIC16F887 microcontroller.
4. Connect the hardware components according to the PCB schematic.


## ✨ Author
**Irimia Petru-Dorin**
- 📧 Email: dorinirimia40@yahoo.com
- 🌍 [LinkedIn](https://www.linkedin.com/in/dorin-dip)

---
> Project made for **Gheorghe Asachi University**, Faculty of Electronics and Telecommunications.

