# Program used for Flash
<sub>Content:</sub><br>
 &emsp;&emsp;[<sub>1. Pickit3 Programmer</sub>](#1-pickit3-programmer)<br>
 &emsp;&emsp;[<sub>2. Where to find the "hex" file in Proteus 8 Professional</sub>](#2-where-to-find-the-hex-file-in-proteus-8-professional)<br>


## 1. Pickit3 Programmer
 > Version: &emsp; <sub>aplication_v3.10</sub>

### First step:
- Extract the folder and read the readme.txt file inside it.
- Install the program using the specified method.
- To connect the Pickit3 device to your microcontroller, look for the wiring diagram for it.
  - This project used the PIC16F887 microcontroller and so, I have attached an image of how it was connected.
  
  ![image](https://github.com/user-attachments/assets/fbc23530-4c50-45a0-9f58-f3f53e46874f)

### Second step:
- Make sure you have connected the pickit3 device to your computer via USB.
- Open the previously installed pickit3 program and access the Tools section from the top bar.
- Click on the "Check Communication" button.
- > After the connection is established, you can try to clear the microcontroller's memory using the "Erase" button. It is located in the main program window.

### Third step:
- From the top bar, select "File" and go to the "Import Hex" section.
- Select the file with the ending "hex" that you want to load into the microcontroller.

### Fourth step:
- From the main page of the program, set the voltage in the box below the "Microchip" logo, and check the boxes labeled "On" and "/MCLR".
- Press the "Write" button and wait for the execution to finish.
- Deselect the "On" and "/MCLR" boxes, which were previously enabled.
  - I have attached a picture of the programming completion. (The "On" case is active to power my circuit).
  
     ![image](https://github.com/user-attachments/assets/48b80b55-4560-4e49-8737-f6eded403390)


## 2. Where to find the "hex" file in Proteus 8 Professional

![image](https://github.com/user-attachments/assets/e8ae811e-c953-4d91-bace-57d31e3a6466)
