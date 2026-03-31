# MSA Phase 2 Hands on: Eureka Heartbeat Exploration


## Task 1: Setting up the environment

To investigate Eureka's heartbeat, we need 2 things:

The ```Eureka Server```, which acts as our registry. 

and the ```Eureka Client```, which are the services we will test the heartbeat on. 


### Step 1: Set up Eureka Server

we will use https://start.spring.io/ to set up our environment. 

when setting up, we ensure the following

```
Project = Maven
Language = Java
Spring Boot = 4.0.4
Group = com.<NAME>
Artifact = eureka-server
Package Name = com.<NAME>.eureka.server
Packaging = Jar
Configuration = YAML
```

under ```dependencies```, press ```add dependencies``` and add ```Eureka Server```

<img width="1491" height="542" alt="image" src="https://github.com/user-attachments/assets/090e5e04-db06-40bd-a2c1-833b963f924b" />

Once set up, we press ```generate```

<img width="623" height="255" alt="image" src="https://github.com/user-attachments/assets/52b9f48a-ba39-46d1-9e82-3ddc968510bc" />

### Step 2: Set up Eureka Client (service)

for this step, follow the same steps previously but this time:

set project name to ```dummy-service```

in the dependencies, add: ```Eureka Discovery Client''' and ```Spring Web```

<img width="1489" height="533" alt="image" src="https://github.com/user-attachments/assets/f3567c97-37a8-4492-8dc2-ce5c14ac95c1" />

<img width="259" height="242" alt="image" src="https://github.com/user-attachments/assets/f71dc5ed-da78-4684-90d0-dea66eca4557" />

---

[Eureka Server:](https://www.geeksforgeeks.org/advance-java/spring-boot-eureka-server/)
The Eureka Server is the registry that holds information regarding all service instances. It enables the service discovery and registration of services.

It supports many things, but in our case, the we focus on the servers built in Health monitoring.


Eureka Discovery Client: What allows for Eureka's high availability, this library is what allows for service registration and discovery



### Step 3: Enable Server and Client

To continue, we need to enable our server and client. 

First, in our ```EurekaServerApplication.java```, we insert ```@EnableEurekaServer``` above the class declaration

<img width="1239" height="598" alt="image" src="https://github.com/user-attachments/assets/b7699987-a0de-48a6-9f32-a2904d097043" />

Then, we do the same thing in ```EurekaDummyServiceApplication.java```, this time, adding ```@EnableDiscoveryClient``` above the class declarion, 

<img width="1157" height="624" alt="image" src="https://github.com/user-attachments/assets/a807ca1f-b3c0-48ad-bac8-4b7994f419de" />


## Task 2: Setting up Config Files

[spring.io](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html) does a lot of the heavy lifting with the provided sample code. 

we need to do two things, set up the application.yaml in both the server and client. 


### Step 1: Set up server application.yaml 

In this step we will define the service registries behavior in our server. 

copying what is given from spring.io, our yaml should look like this:
<img width="668" height="325" alt="image" src="https://github.com/user-attachments/assets/065bb506-3b16-49ef-93b5-4da0ba87c55d" />

we also added two lines that will be explained

**Explanation:**

```Server
      port: 8761  
this sets the registries communication port. 8761 is the standard for Eureka.
```
```
  server:
    [enable-self-preservation](https://www.baeldung.com/eureka-self-preservation-renewal): true

this is what will prevent the registry from removing a service that falls below the heartbeat threshold 
```

```
  client:
      registerWithEureka: false
            **stops Eureka from registering itself with a neighbor, because every server is also a client**
      fetchRegistry: false
            **stops eureka from fetching the registry it already has from itself**
      serviceUrl:
            defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
            **local registry endpoint**

```

### Step 2: Set up Client application.yaml

<img width="481" height="345" alt="image" src="https://github.com/user-attachments/assets/54659f81-6df9-4e43-bf2c-9b57e0ecf41f" />

these are a bit similar to the server, but note some things:

we set the ```port``` to 8081

and under ```instance```, we have a setting to prefer IP address, which just saves a headache in cases where we run a vpn. 

most importantly, we have ```eureka.instance.lease-renewal-interval-in-seconds=30```, the basis of what we are testing in this hands on portion. 


## Task 3: Start Server and Client

This task is simple, in the service project, run ```.\mvnw spring-boot:run```

and in the server, also run ```.\mvnw spring-boot:run```

<img width="1461" height="377" alt="image" src="https://github.com/user-attachments/assets/28e73eea-7662-4aa1-b50c-6ec0d41091cd" />

once successfull, we go to ```http://localhost:8761/``` and see the following

<img width="1919" height="916" alt="122212121212" src="https://github.com/user-attachments/assets/46252dfa-ac5e-4718-a3b6-c8f156e0f5c9" />


note that our dummy service has been successfully registered and show as ```UP```, and we also see our renewal threshold and that lease expiration is not enabled. 


## Task 4: Graceful shutdown 

to initiate a graceful shutdown, we can do ```ctrl + C``` in the terminal for our service, doing so sends a cancel signal to eureka, lowering the renewal threshold to 1, and immediately removes the service from the registry. 

<img width="1895" height="646" alt="ydfrsg3et45" src="https://github.com/user-attachments/assets/d92660b7-8323-45d9-a531-5a145061b48e" />


## Task 5: Making the Zombie Instance

Recalling from the Eureka presentation, where we pointed out a potential flaw in Eureka's heartbeat mechanism (see diagram).

<img width="1320" height="515" alt="image" src="https://github.com/user-attachments/assets/762f3b8d-4aa7-4463-bfad-e55acb4bb625" />

Here, we will attempt to replicate this by simulating a service crash. 



