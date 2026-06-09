import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class EmetteurAgent extends Agent {
    private static final long serialVersionUID = 1L;
    int counter = 0;


    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Alice", AID.ISLOCALNAME));
                if (counter == 1) {
                    msg.setContent("Quitter");
                    send(msg);
                    System.out.println("Message Quitter envoye. Bob se supprime aussi.");
                    stop();        // ← stops the TickerBehaviour
                    doDelete();    // ← kills Bob agent
                    return;
                } else {
                    msg.setContent("Hello, es-tu la ?");
                    counter++;
                }
                send(msg);
                System.out.println("Message envoye a Alice.");
            }
        });
    }
}