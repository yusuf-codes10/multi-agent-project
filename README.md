# TP2 — Multi-Agent JADE

A simple JADE multi-agent system with two agents: **Bob** (sender) and **Alice** (receiver).

---

## Getting Started

### Prerequisites

- Java JDK installed
- `jade.jar` present in `lib/`

### Clone

```bash
git clone https://github.com/yusuf-codes10/multi-agent-project
cd TP2
```

### Commands

```bash
make compile   # Compile all .java files from src/ into bin/
make run       # Launch the JADE platform with Alice and Bob
make clean     # Delete compiled .class files from bin/
```

---

## Project Structure

```
TP2/
├── src/
│   ├── EmetteurAgent.java    # Bob — sends messages
│   └── RecepteurAgent.java   # Alice — receives messages
├── bin/                      # Compiled output (auto-generated, do not edit)
├── lib/
│   └── jade.jar              # JADE runtime library
├── Makefile
└── README.md
```

---

## Exercises

### Exercise 1 — TickerBehaviour (every 5 seconds)

**File:** `EmetteurAgent.java`

Instead of sending a message once with `OneShotBehaviour`, Bob now uses `TickerBehaviour` to send a message every 5 seconds.

```java
addBehaviour(new TickerBehaviour(this, 5000) {
    protected void onTick() {
        // sends message every 5 seconds
    }
});
```

---

### Exercise 2 — Quit on "Quitter" (doDelete)

**File:** `RecepteurAgent.java`

After receiving a message, Alice checks if the content is `"Quitter"`. If so, she deletes herself using `doDelete()`.

```java
if (msg.getContent().equals("Quitter")) {
    doDelete();
    return;
}
```

Bob also stops his ticker and deletes himself after sending `"Quitter"`:

```java
stop();      // stops the TickerBehaviour
doDelete();  // kills Bob agent
```

---

### Exercise 3 — Directory Facilitator (DF) lookup

**Files:** `EmetteurAgent.java` and `RecepteurAgent.java`

Instead of hardcoding `"Alice"` as the receiver, Bob searches the DF for any agent offering a service of type `"Imprimante"`.

Alice registers herself in the DF on startup:

```java
ServiceDescription sd = new ServiceDescription();
sd.setType("Imprimante");
DFService.register(this, dfd);
```

Alice also deregisters cleanly when she shuts down:

```java
protected void takeDown() {
    DFService.deregister(this);
}
```

Bob searches the DF each tick instead of using a hardcoded name:

```java
DFAgentDescription[] results = DFService.search(myAgent, template);
AID imprimante = results[0].getName();
```

---

## Notes

- Always call `block()` in `CyclicBehaviour` when no message is received, to avoid burning CPU.
- `bin/` is auto-generated and should be in `.gitignore`.