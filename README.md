# Raspberry_Meteo
Java and PHP code for a Raspberry Meteo station

In the projects several executable are provided:

1) raspyMeteo -> this is the main meteo station application, to be run exclusively on the Raspberry Pi board (TBC).

2) testSensor -> this is an application to test the humidity and temperature sensor readout. To be double checked if is it mandatory to run in on a PC or it can be executed on the Raspberry Pi too.

3) fillTable -> application to create the table to be written into the mySql database. Table are filled with dummy data, since it is meant to test the dB interface from java. It must be run on the Raspberry Pi.

4) testDB -> application to just test the mySql connection. It must be run on the Raspberry Pi.