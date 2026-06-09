import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RecepteurAgent extends Agent {
    private static final long serialVersionUID = 1L;

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " est pret.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println("Message recu de " + msg.getSender().getLocalName() + " : " + msg.getContent());

                    // ...
                    if (msg.getContent().equals("Quitter")) {
                        System.out.println("Message Quitter recu. Alice se supprime.");
                        doDelete();
                        return;
                    }

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Bien recu !");
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}