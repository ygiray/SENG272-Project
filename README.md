# ISO 15939 Measurement Process Simulator
Student Name : Yusuf Giray Çakıcı
# Student ID : 202328016  
---------------------------------------------------------------------------------------------
This project is a software measurement simulator developed based on the ISO/IEC 15939 standard. It automates the process of collecting, analyzing, and visualizing software quality data through a structured 5-step wizard.
---------------------------------------------------------------------------------------------
Key Features (The 5-Step Process)

1-Profile: Captures user identity, institution, and session details.

2-Define: Implements mutual exclusivity for selecting Quality Types (Product/Process) and Scenarios (Education/Health/Custom) as per ISO 15939.

3-Plan: Provides a read-only summary of the measurement plan, showing dimensions, metrics, and their respective coefficients.

4-Collect: Allows real-time raw data entry with automatic 1-5 score normalization based on metric directions (Higher/Lower is better).

5-Analyse: Performs weighted average calculations, Gap Analysis, and visualizes results using a custom-drawn Radar Chart.
---------------------------------------------------------------------------------------------

How to Run
The project does not require any external libraries or dependencies.

Via Terminal/Command Prompt:

1-Navigate to the project root directory:
Bash
cd "Seng272_ISO15939_Simulator"

2-Compile all source files:

Bash
javac *.java

3-Run the application:

Bash
java Main
---------------------------------------------------------------------------------------------
AI Collaboration & Acknowledgement
This project was developed with the assistance of Google Gemini (AI) as a collaborative partner.

