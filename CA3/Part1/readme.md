# Class Assignment 3 Part 1

## Introduction

The goal of this class assignment was to clone our personal repositories to a virtual machine running Ubuntu 22.04 in
VirtualBox and attempt to run the projects in them.
The final result of the assignment can be found [here](https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-).

## Table of Contents

1. [Getting Started](#Getting-started)
2. [Implementing Changes](#Implementing-Changes)
    - [Part 1: Cloning the repository to the VM](#Part-1-Cloning-the-repository-to-the-VM)
    - [Part 2: Running the CA1 project](#Part-2-Running-the-CA1-project)
    - [Part 3: Running the CA1 Part1 project](#Part-3-Running-the-CA2-Part1-project)
    - [Part 4: Running the CA2 Part2 project](#Part-4-Running-the-CA2-Part2-project)
3. [Final Remarks](#Final-Remarks)

## Getting Started

The first step is to create a new virtual machine and install Ubuntu 22.04 then run the following commands to install
JDK and Git:

```bash
sudo apt update
```

```bash
sudo apt install openjdk-17-jdk
```

```bash
sudo apt-get install git-all
```

## Implementing Changes

### Part 1: Cloning the repository to the VM

The first part of the assignment is to clone the repository to the virtual machine. To do so, first it is necessary to
add a SSH key to the GitHub account. The steps to do so are:

1. Open the terminal and generate a new SSH key:

```bash
 ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```

*Note*: Replace the email with the email associated with the GitHub account.

2. Navigate to the folder where the SSH key was generated:

```bash
cd ~/.ssh
```

3. Display the SSH key:

```bash
cat id_rsa.pub
```

4. Copy the SSH key and add it to the GitHub account:
    - Go to the GitHub website and log in.
    - Click on the profile icon in the top right corner and select "Settings".
    - Click on "SSH and GPG keys" in the left sidebar.
    - Click on "New SSH key".
    - Paste the SSH key in the "Key" field and click on "Add SSH key".

5. Clone the repository to the virtual machine:

```bash
git clone "your_repository_url"
```

*Note*: Replace the repository URL with the URL of the repository to be cloned.

### Part 2: Running the  CA1 project

1. After cloning the repository to the virtual machine, the first step is to change the permissions of the mvnw file to
   make it executable. To do so, navigate to your project folder and run the following command:

```bash
chmod +x mvnw
```

2. After changing the permissions, the project can be compiled by running the following command:

```bash
./mvnw build
```

3. Check the ip of your VM by running the following command:

```bash
ip addr
```

4. In your browser access the following url:

```bash
<your-ip>:8080
```

### Part 3: Running the CA2 Part1 project

1. After navigating to the chat server folder, run the following command to compile the project:

```bash
./gradlew build
```

2. Start the chat server in the virtual machine by running the following command:

```bash
./gradlew runServer
```

*Note*: The chat server will be running on port 59001 by default, if you wish to run it on a different port, you can
specify it as an argument when running the command:

```bash
./gradlew runServer --args="port"
```

3. Check the ip of your VM by running the following command:

```bash
ip addr
```

4. In your machine's terminal, navigate to your local folder where the project is located and run the following command:

```bash
./gradlew runClient --args="your-ip port"
```

*Note*: Replace "your-ip" with the ip of your VM and "port" with the port the chat server is running on.

### Part 4: Running the CA2 Part2 project

1. After cloning the repository to the virtual machine, the first step is to change the permissions of the gradlew and
   gradlew files to make it executable. To do so, navigate to your project folder and run the following command:

```bash
chmod +x gradlew
```

2. After changing the permissions, the project can be compiled by running the following command:

```bash
./gradlew build
./gradlew bootRun
```

3. Check the ip of your VM by running the following command:

```bash
ip addr
```

4. In your browser access the following url:

```bash
<your-ip>:8080
```

## Final Remarks

During this assignment, the first version of Ubuntu used on the VM was version 18.04 but was incompatible with the
projects used since they required node version 12 at least, which is not supported by Ubuntu 18. After upgrading to
Ubuntu 22.04 and upgrading Node to version 12, the projects ran smoothly and without any problems.


