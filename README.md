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

Eureka Server: 




