import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class RecepteurAgent extends Agent {
    private static final long serialVersionUID = 1L;

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " est pret.");

        // Register "Imprimante" service in the DF
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Imprimante");
        sd.setName("service-impression");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            System.out.println("Alice enregistree dans le DF comme Imprimante.");
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println("Message recu de " + msg.getSender().getLocalName() + " : " + msg.getContent());

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

    protected void takeDown() {
        try {
            DFService.deregister(this);
            System.out.println("Alice desenregistree du DF.");
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}