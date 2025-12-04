# Sensor-Data-Dashboard
A Java-based engineering project that visualizes and analyzes January 2025 sensor data through an interactive Swing dashboard. The system integrates data parsing, real-time graphical representation and anomaly identification to deliver an intuitive desktop interface for monitoring environmental and operational sensor metrics.


**Features**

1)Java Swing Dashboard UI for clean and interactive visualization

2)CSV Data Parsing for January 2025 sensor readings

3)Line Charts & Graphical Plots for trend analysis

4)Anomaly Detection to spot unusual sensor behavior

5)Modular Code Structure for scalability and maintainability

6)Real-time UI Rendering for smoother user interaction

**Tech Stack**

1)Java (JDK 8 or later)

2)Java Swing – UI components

3)AWT / Swing Timers – rendering

4)CSV Parsing (OpenCSV or custom parser)

5)IntelliJ / Eclipse / NetBeans – recommended IDE

**Project Structure**

/src
 ├── dashboard/
 │    ├── SensorDashboard.java
 │    ├── ChartPanel.java
 │    └── DataController.java
 ├── data/
 │    └── sensor_data_jan2025.csv
 └── utils/
      └── CSVReader.java

** How to Run**

**Clone the repository:**

git clone https://github.com/your-username/your-repo-name.git

Open the project in your IDE (IntelliJ recommended).

Ensure JDK 8+ is configured.

**Run the main file:**

SensorDashboard.java


The Swing UI will launch with all sensor graphs and analytics.

**Data Used**

**Dataset**: sensor_data_jan2025.csv

**Description**: Daily sensor readings used for performance monitoring and trend analysis.

**Future Enhancements**

1)Add predictive analytics using ML models

2)Implement live sensor data streaming

3)Add export functionality for reports

4)Enhance UI with JavaFX or advanced Swing components
