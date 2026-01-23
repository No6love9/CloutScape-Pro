# CloutScape RuneLite Plugin - Kali Linux Setup Guide

This guide provides the exact steps to set up, build, and launch the CloutScape plugin on **Kali Linux**.

## 1. Prerequisites
Ensure your Kali system has the necessary development tools installed.

```bash
# Update package lists
sudo apt update

# Install OpenJDK 11 (RuneLite standard)
sudo apt install -y openjdk-11-jdk

# Install Maven for building the project
sudo apt install -y maven

# Verify installations
java -version
mvn -version
```

## 2. Clone and Build
Clone the repository and build the JAR file.

```bash
# Clone the repository
git clone https://github.com/No6love9/CloutScape-Pro.git
cd CloutScape-Pro

# Build the project
mvn clean package
```

## 3. Locate the JAR
After a successful build, your JAR file will be located at:
`target/CloutScape_RuneLite_Edition.jar`

## 4. Launching in Developer Mode
To launch RuneLite with the plugin loaded in developer mode:

```bash
mvn exec:java -Dexec.mainClass="net.runelite.client.RuneLite" -Dexec.args="--developer-mode"
```

## 5. Troubleshooting Kali-Specific Issues
- **Permissions**: If you encounter permission errors, ensure you have ownership of the directory: `sudo chown -R $USER:$USER .`
- **Java Version**: If `mvn` uses the wrong Java version, set it explicitly:
  `export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64`
- **Missing Dependencies**: If Maven fails to download dependencies, check your internet connection or try `mvn package -U` to force an update.

---
*Optimized for Kali Linux 2024/2025/2026.*
