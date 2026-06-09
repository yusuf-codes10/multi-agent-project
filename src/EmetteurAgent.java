import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class EmetteurAgent extends Agent {
    private static final long serialVersionUID = 1L;

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Alice", AID.ISLOCALNAME));
//                msg.setContent("Hello, es-tu la ?");
                msg.setContent("Quitter");
                send(msg);
                System.out.println("Message envoye a Alice.");
            }
        });
    }
}