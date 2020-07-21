- Installation:
	Git:
	1. Install "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\Git-2.23.0-64-bit.exe" 
	2. Install "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\KDiff3-64bit-Setup_0.9.98-2.exe"
	3. Install "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\GitExtensions-3.1.1.6049.msi" 
	4. Run the command: git config --global http.sslVerify false
	5. Clone Project with DevLab Account <USER> and <PASS>: git clone https://<USER>:<PASS>@git.devlab.de.tmo/adev_sb.git c:\Projects\AdevSB

	Project:
	1. Unzip gradle: "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\gradle-5.6-bin.zip" Dest: "c:\Gradle\gradle-5.6"
	2. Add gradle to Path: "c:\Gradle\gradle-5.6\bin"
	3. Install JDK: "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\jdk-8u181-windows-x64.exe"
	4. Run command: gradle
	5. Copy "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\gradle.properties" Dest: "c:\Users\H<username>\.gradle\"
	6. Open "c:\Users\H<username>\.gradle\gradle.properties"
	7. Set the user (H<username>) and password for: "systemProp.https.proxyUser" and "systemProp.https.proxyPassword" 
	8. Idea install, open
	9. Idea: Open menu: "File/Open" "c:\Projects\AdevSB\BaseMS"
		9.a. Set gradle home: "c:\Gradle\gradle-5.6"
		9.a. Set JDK: "c:\Program Files\Java\jdk1.8.0_181"
	10. Idea: Set JDK for project: "File/Project structure/Project Settings/Project/Project" SDK (add JDK at "File/Project structure/Platform Settings/SDKs +"
	11. Idea: Set code style: "File/Settings/Editor/Code style/Java/Schema(gear)" Import schema: "k:\SI_POP\SI PoP_Team_fileshare-I\ADEV_BIG_DATA_BUD\intellij_code_style.xml"
	12. Idea: Suggestion for remove unused import: "File/Settings/Editor/General/Auto Import/Optimize imports on the fly"
	13. Idea: Set Configuration for JUnit templates: "Run/Edit Configuration/Templates/JUnit/Shorten Command line:" to JAR manifest


