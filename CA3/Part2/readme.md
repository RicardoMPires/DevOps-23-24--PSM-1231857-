# Class Assignment 3 Part 1

## Introduction

The goal of this class assignment was to use Vagrant to create and set up virtual machines to run our projects in.
The final result of the assignment can be found [here](https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-).

## Table of Contents

1. [Getting Started](#Getting-started)
2. [Implementing Changes](#Implementing-Changes)
    - [Chaing the Vagrantfile](#changing-the-vagrantfile)
    - [Part 2: Running the CA1 project](#Part-2-Running-the-CA1-project)

3. [Final Remarks](#Final-Remarks)

## Getting Started

To get started, first, it is required to have Vagrant and VirtualBox installed on your machine.
After installing the necessary software, download the vagrantfile
located [here](https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/).

## Implementing Changes

### Changing the Vagrantfile

1. The first step is to change the Vagrantfile to include the necessary configurations for the virtual machine. The
   Vagrantfile provided in the repository already has the necessary configurations for the virtual machine, such as the
   box, the memory, and the IP address.
   We need to change the vagrantfile to include the link to our repository and the necessary configurations to run the
   projects. Note that for this to work, the repository must be public.

2. After making all the necessary changes to the Vagrantfile, it should look something like this:

```bash
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
# for information about machine names on private network
Vagrant.configure("2") do |config|
config.vm.box = "generic/ubuntu2204"

# This provision is common for both VMs
config.vm.provision "shell", inline: <<-SHELL
sudo apt-get update -y
sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
openjdk-17-jdk-headless
# ifconfig
SHELL

#============
# Configurations specific to the database VM
config.vm.define "db" do |db|
db.vm.box = "generic/ubuntu2204"
db.vm.hostname = "db"
db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setiing H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
end

#============
# Configurations specific to the webserver VM
config.vm.define "web" do |web|
web.vm.box = "generic/ubuntu2204"
web.vm.hostname = "web"
web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memmory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      # sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-.git
      cd DevOps-23-24--PSM-1231857-/CA2/Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      rm -rf node_modules
      npm install
      npx webpack --config webpack.config.js
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
end
end

```

*Note:* Due to the version used in the virtual machine (Ubuntu 22.04), there was an error occurring when trying to use
the webpack, which is why there are extra lines to manually install the webpack through npx.

3. After making all the changes to the Vagrantfile, open a terminal and navigate to the project folder(in this case
   CA3/Part2) and start the virtual machines with the command:

```bash
vagrant up
```

4. Open your browser and access the ip and port of the web virtual machine stated in the Vagrantfile, by default it is:

```bash
192.168.56.10:8080
```

5. The final result should look something like this:
   ![img_8.png](Images/img_8.png)

## Alternative implementation solution

Instead of using VirtualBox, it is possible to use Hyper-V to create the virtual machines. To do so, it is necessary to
change the provider in the Vagrantfile to "hyperv" and add the necessary configurations for Hyper-V. The Vagrantfile
should look something like this:

```bash
Vagrant.configure("2") do |config|
  # Specify the base box. Make sure this box is compatible with Hyper-V.
  config.vm.box = "hypervv/Ubuntu2004"

  # Default provider configuration
  config.vm.provider "hyperv" do |hv|
    hv.cpus = 2          # Number of CPUs
    hv.memory = 1024     # Memory size in MB
    hv.maxmemory = 2048  # Maximum dynamic memory in MB
    hv.linked_clone = true # Use linked clones to speed up VM creation
  end

  # General provisioning script that runs on all VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-17-jdk-headless
  SHELL

  # Configuration for the database VM
  config.vm.define "db" do |db|
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # Specific provisioning for the database VM
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
      java -cp h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/h2-server.log 2>&1 &
    SHELL
  end

  # Configuration for the webserver VM
  config.vm.define "web" do |web|
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # Webserver specific provisioning
    web.vm.provision "shell", inline: <<-SHELL
      sudo apt install -y tomcat9 tomcat9-admin
      echo "Tomcat installed."

      # Clone the repository
      git clone https://github.com/RicardoMPires/DevOps-23-24--PSM-1231857-.git
      sudo chown -R vagrant:vagrant /home/vagrant/DevOps-23-24-JPE-1130122/
      
      cd DevOps-23-24--PSM-1231857-/CA2/Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      rm -rf node_modules
      npm install
      npx webpack --config webpack.config.js
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
  end
end
```

## Final Remarks

For some reason, the webpack was not working properly in the virtual machine, which is why there are extra lines to
manually install the webpack through npx. This is a workaround to the problem, and it is not the best solution. The best
solution would be to find the root cause of the problem and fix it.


