import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RecepteurAgent extends Agent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
        System.out.println("Agent " + getLocalName() + " est prêt.");

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println("Message reçu de " + msg.getSender().getLocalName() + " : " + msg.getContent());
                    
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Bien reçu !");
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
